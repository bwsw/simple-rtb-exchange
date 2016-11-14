package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.{Site, Status}
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}
import com.bitworks.rtb.service.Logging
import com.bitworks.rtb.service.dao.schema.{SiteCategoryEntity, SiteEntity}

/**
  * DAO for [[com.bitworks.rtb.model.db.Site Site]].
  *
  * @author Egor Ilchenko
  */
trait SiteDao extends BaseDao[Site] with CacheHelper[Site]

/**
  * DAO implementation for [[com.bitworks.rtb.model.db.Site Site]].
  *
  * @param ctx          DB context
  * @param updater      [[com.bitworks.rtb.service.dao.CacheUpdater CacheUpdater]]
  */
class SiteDaoImpl(ctx: DbContext, val updater: CacheUpdater) extends SiteDao with Logging {

  import ctx._

  val siteType = 1

  override def notify(action: CacheMessage) = {
    val apps = action match {
      case InitCache => ctx.run {
        Schema.site
          .filter(_.`type` == lift(siteType))
          .filter(!_.deleted)
      }
      case UpdateCache => ctx.run {
        Schema.site
          .filter(_.`type` == lift(siteType))
          .filter(_.tsversion >= lift(tsversion))
      }
    }
    updateCache(apps, getCreator)
  }

  /** Returns function creates [[com.bitworks.rtb.model.db.Site Site]] */
  private def getCreator: SiteEntity => Option[Site] = createSite(
    ctx.run(Schema.siteCategory))

  /**
    * Creates [[com.bitworks.rtb.model.db.Site Site]] from
    * [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    *
    * @param sc     all site categories from DB
    * @param entity [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    * @return created [[com.bitworks.rtb.model.db.Site Site]]
    */
  private def createSite(
      sc: Seq[SiteCategoryEntity])(
      entity: SiteEntity): Option[Site] = {

    val status = Status(entity.status)
    val domain = entity.domain match {
      case Some(d) => d
      case _ =>
        log.error(s"site id: ${entity.id} not loaded, empty domain")
        return None
    }

    val siteCategories = sc.filter(_.siteId == entity.id).map(_.iabCategoryId)
    Some(
      Site(
        entity.id,
        entity.name,
        entity.publisherId,
        status,
        entity.privacyPolicy,
        entity.test,
        domain,
        entity.keyword,
        siteCategories))
  }
}
