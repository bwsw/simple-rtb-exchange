package com.bitworks.rtb.service.dao.entitydao

import com.bitworks.rtb.model.DataValidationException
import com.bitworks.rtb.service.dao.schema.{SiteCategoryEntity, SiteEntity}
import com.bitworks.rtb.service.dao.{BaseDao, CacheHelper, CacheUpdater, DbContext}
import com.bitworks.rtb.model.db.{App, IABCategory, Status}
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}

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
  * @param updater     [[CacheUpdater CacheUpdater]]
  * @param categoryDao [[com.bitworks.rtb.service.dao.entitydao.CategoryDao CategoryDao]]
  */
class AppDaoImpl(
    ctx: DbContext,
    val updater: CacheUpdater,
    categoryDao: CategoryDao) extends AppDao {

  import ctx._

  val appType = 2

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
  private def getCreator = createApp(
    ctx.run(Schema.siteCategory),
    categoryDao.getAll)(_)


  /**
    * Creates [[com.bitworks.rtb.model.db.App App]] from
    * [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    *
    * @param ac         all app categories from DB
    * @param categories all categories from DB
    * @param entity     [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    * @return created [[com.bitworks.rtb.model.db.App App]]
    */
  private def createApp(
      ac: Seq[SiteCategoryEntity],
      categories: Seq[IABCategory])(entity: SiteEntity) = {
    val status = Status(entity.status)

    val bundle = entity.appBundle match {
      case Some(b) => b
      case _ => throw new DataValidationException(s"app id: ${entity.id}. Empty bundle")
    }
    val storeUrl = entity.appStoreUrl match {
      case Some(s) => s
      case _ => throw new DataValidationException(s"app id: ${entity.id}. Empty store url")
    }
    val version = entity.appVer match {
      case Some(v) => v
      case _ => throw new DataValidationException(s"app id: ${entity.id}. Empty version")
    }

    val catIds = ac.filter(_.siteId == entity.id).map(_.iabCategoryId)
    val appCategories = categories.filter(x => catIds.contains(x.id))

    App(
      entity.id,
      entity.name,
      status,
      entity.privacyPolicy,
      entity.test,
      entity.domain,
      entity.keyword,
      appCategories,
      bundle,
      storeUrl,
      version
    )
  }
}
