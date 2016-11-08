package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.Publisher
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}
import com.bitworks.rtb.service.dao.schema._

/**
  * DAO for [[com.bitworks.rtb.model.db.Publisher Publisher]].
  *
  * @author Egor Ilchenko
  */
trait PublisherDao extends BaseDao[Publisher] with CacheHelper[Publisher]

/**
  * DAO implementation for [[com.bitworks.rtb.model.db.DisplayManager DisplayManager]].
  *
  * @param ctx            DB context
  * @param updater        [[com.bitworks.rtb.service.dao.CacheUpdater CacheUpdater]]
  * @param iabCategoryDao [[com.bitworks.rtb.service.dao.CategoryDao CategoryDao]]
  */
class PublisherDaoImpl(
    ctx: DbContext,
    val updater: CacheUpdater,
    iabCategoryDao: CategoryDao) extends PublisherDao {

  import ctx._

  override def notify(action: CacheMessage) = {
    val publishers = action match {
      case InitCache => ctx.run {
        Schema.publisher
          .filter(!_.deleted)
      }
      case UpdateCache => ctx.run {
        Schema.publisher
          .filter(_.tsversion > lift(tsversion))
      }
    }
    updateCache(publishers, getCreator)
  }

  /** Returns function creates [[com.bitworks.rtb.model.db.Publisher Publisher]] */
  private def getCreator = createPublisher(
    ctx.run(Schema.publisherCategory),
    ctx.run(Schema.publisherBlockedCategory),
    ctx.run(Schema.publisherBlockedAdvertiser))(_)

  /**
    * Creates [[com.bitworks.rtb.model.db.App App]] from
    * [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    *
    * @param pc     all publishers categories from DB
    * @param pbc    all publishers blocked categories from DB
    * @param ba     all blocked advertisers from DB
    * @param entity [[com.bitworks.rtb.service.dao.schema.PublisherEntity PublisherEntity]]
    * @return created [[com.bitworks.rtb.model.db.Publisher Publisher]]
    */
  private def createPublisher(
      pc: Seq[PublisherCategoryEntity],
      pbc: Seq[PublisherBlockedCategoryEntity],
      ba: Seq[PublisherBlockedAdvertiserEntity])(
      entity: PublisherEntity) = {

    val publisherCategories = iabCategoryDao
      .getByIds(pc.filter(_.publisherId == entity.id).map(_.iabCategoryId))

    val blockedCategories = iabCategoryDao
      .getByIds(pbc.filter(_.publisherId == entity.id).map(_.iabCategoryId))

    val blockedDomains = ba.filter(_.publisherId == entity.id).map(_.domain)

    Some(
      Publisher(
        entity.id,
        entity.name,
        publisherCategories,
        entity.domain,
        blockedDomains,
        blockedCategories))
  }
}
