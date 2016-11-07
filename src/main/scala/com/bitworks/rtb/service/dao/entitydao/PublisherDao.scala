package com.bitworks.rtb.service.dao.entitydao

import com.bitworks.rtb.service.dao.{BaseDao, CacheHelper, CacheUpdater, DbContext}
import com.bitworks.rtb.service.dao.schema._
import com.bitworks.rtb.model.db.{IABCategory, Publisher}
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}

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
  * @param updater        [[CacheUpdater CacheUpdater]]
  * @param iabCategoryDao [[com.bitworks.rtb.service.dao.entitydao.CategoryDao]]
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
          .filter(!_.deleted)
          .filter(_.tsversion > lift(tsversion))
      }
    }
    updateCache(publishers, getCreator)
  }

  /** Returns function creates [[com.bitworks.rtb.model.db.Publisher Publisher]] */
  private def getCreator = createPublisher(
    ctx.run(Schema.publisherCategory),
    ctx.run(Schema.blockedCategory),
    ctx.run(Schema.blockedAdvertiser),
    iabCategoryDao.getAll)(_)

  /**
    * Creates [[com.bitworks.rtb.model.db.App App]] from
    * [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    *
    * @param pc all publishers categories from DB
    * @param pbc all publishers blocked categories from DB
    * @param ba all blocked advertisers from DB
    * @param categories all categories from DB
    * @param entity     [[com.bitworks.rtb.service.dao.schema.PublisherEntity PublisherEntity]]
    * @return created [[com.bitworks.rtb.model.db.Publisher Publisher]]
    */
  private def createPublisher(
      pc: Seq[PublisherCategoryEntity],
      pbc: Seq[PublisherBlockedCategoryEntity],
      ba: Seq[PublisherBlockedAdvertiserEntity],
      categories: Seq[IABCategory])(
      entity: PublisherEntity): Publisher = {
    val catIds = pc.filter(_.publisherId == entity.id).map(_.iabCategoryId)
    val publisherCategories = categories.filter(x => catIds.contains(x.id))

    val blockedCatIds = pbc.filter(_.publisherId == entity.id).map(_.iabCategoryId)
    val blockedCategories = categories.filter(x => blockedCatIds.contains(x.id))
    val blockedDomains = ba.filter(_.publisherId == entity.id).map(_.domain)

    Publisher(
      entity.id,
      entity.name,
      publisherCategories,
      entity.domain,
      blockedDomains,
      blockedCategories)
  }
}
