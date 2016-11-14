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
  * DAO implementation for [[com.bitworks.rtb.model.db.Publisher Publisher]].
  *
  * @param ctx     DB context
  * @param updater [[com.bitworks.rtb.service.dao.CacheUpdater CacheUpdater]]
  */
class PublisherDaoImpl(ctx: DbContext, val updater: CacheUpdater) extends PublisherDao {

  import ctx._

  override def notify(action: CacheMessage) = {
    val publishers = action match {
      case InitCache => ctx.run {
        Schema.publisher
          .filter(!_.deleted)
      }
      case UpdateCache => ctx.run {
        Schema.publisher
          .filter(_.tsversion >= lift(tsversion))
      }
    }
    updateCache(publishers, getCreator(publishers))
  }

  /** Returns function creates [[com.bitworks.rtb.model.db.Publisher Publisher]] */
  private def getCreator(
      publishers: Seq[PublisherEntity]): PublisherEntity => Option[Publisher] = {
    val ids = publishers.map(_.id)
    val categories = ctx.run {
      Schema.publisherCategory
        .filter(pc => liftQuery(ids).contains(pc.publisherId))
    }
    val blockedCategories = ctx.run {
      Schema.publisherBlockedCategory
        .filter(pbc => liftQuery(ids).contains(pbc.publisherId))
    }
    val blockedAdvertisers = ctx.run {
      Schema.publisherBlockedAdvertiser
        .filter(pba => liftQuery(ids).contains(pba.publisherId))
    }
    createPublisher(
      categories,
      blockedCategories,
      blockedAdvertisers)
  }

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

    val publisherCategories = pc.filter(_.publisherId == entity.id).map(_.iabCategoryId)

    val blockedCategories = pbc.filter(_.publisherId == entity.id).map(_.iabCategoryId)

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
