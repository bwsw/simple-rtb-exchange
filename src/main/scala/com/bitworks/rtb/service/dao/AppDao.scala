package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.{App, IABCategory, Status}
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}
import com.bitworks.rtb.service.Logging
import com.bitworks.rtb.service.dao.schema.{SiteCategoryEntity, SiteEntity}

/**
  * DAO for [[com.bitworks.rtb.model.db.App App]].
  *
  * @author Egor Ilchenko
  */
trait AppDao extends BaseDao[App] with CacheHelper[App]

/**
  * DAO implementation for [[com.bitworks.rtb.model.db.App App]].
  *
  * @param ctx         DB context
  * @param updater     [[com.bitworks.rtb.service.dao.CacheUpdater CacheUpdater]]
  * @param categoryDao [[com.bitworks.rtb.service.dao.CategoryDao CategoryDao]]
  * @param publisherDao [[com.bitworks.rtb.service.dao.PublisherDao PublisherDao]]
  */
class AppDaoImpl(
    ctx: DbContext,
    val updater: CacheUpdater,
    categoryDao: CategoryDao,
    publisherDao: PublisherDao) extends AppDao with Logging {

  val appType = 2

  import ctx._

  override def notify(action: CacheMessage) = {
    val apps = action match {
      case InitCache => ctx.run {
        Schema.site
          .filter(_.`type` == lift(appType))
          .filter(!_.deleted)
      }
      case UpdateCache => ctx.run {
        Schema.site
          .filter(_.`type` == lift(appType))
          .filter(_.tsversion > lift(tsversion))
      }
    }
    updateCache(apps, getCreator)
  }

  /** Returns function creates [[com.bitworks.rtb.model.db.App App]] */
  private def getCreator = createApp(ctx.run(Schema.siteCategory))(_)

  /**
    * Creates [[com.bitworks.rtb.model.db.App App]] from
    * [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    *
    * @param ac     all app categories from DB
    * @param entity [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    * @return created [[com.bitworks.rtb.model.db.App App]]
    */
  private def createApp(ac: Seq[SiteCategoryEntity])(entity: SiteEntity): Option[App] = {
    val status = Status(entity.status)

    val bundle = entity.appBundle match {
      case Some(b) => b
      case None =>
        log.error(s"app id: ${entity.id} not loaded, empty bundle")
        return None
    }
    val storeUrl = entity.appStoreUrl match {
      case Some(s) => s
      case None =>
        log.error(s"app id: ${entity.id} not loaded, empty store url")
        return None
    }
    val version = entity.appVer match {
      case Some(v) => v
      case None =>
        log.error(s"app id: ${entity.id} not loaded, empty version")
        return None
    }

    val publisher = publisherDao.get(entity.publisherId) match {
      case Some(pub) => pub
      case None =>
        log.error(s"app id: ${entity.id} not loaded, publisher not exist")
        return None
    }

    val appCategories = categoryDao
      .getByIds(ac.filter(_.siteId == entity.id).map(_.iabCategoryId))

    Some(
      App(
        entity.id,
        entity.name,
        publisher,
        status,
        entity.privacyPolicy,
        entity.test,
        entity.domain,
        entity.keyword,
        appCategories,
        bundle,
        storeUrl,
        version))
  }
}
