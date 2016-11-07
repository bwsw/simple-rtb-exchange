package com.bitworks.rtb.service.dao.entitydao

import com.bitworks.rtb.model.DataValidationException
import com.bitworks.rtb.service.dao.schema.{SiteCategoryEntity, SiteEntity}
import com.bitworks.rtb.service.dao.{BaseDao, CacheHelper, CacheUpdater, DbContext}
import com.bitworks.rtb.model.db.{IABCategory, Site, Status}
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}

/**
  * DAO for [[com.bitworks.rtb.model.db.Site Site]].
  *
  * @author Egor Ilchenko
  */
trait SiteDao extends BaseDao[Site] with CacheHelper[Site]

/**
  * DAO implementation for [[com.bitworks.rtb.model.db.Site Site]].
  *
  * @param ctx         DB context
  * @param updater     [[CacheUpdater CacheUpdater]]
  * @param categoryDao [[com.bitworks.rtb.service.dao.entitydao.CategoryDao CategoryDao]]
  */
class SiteDaoImpl(
    ctx: DbContext,
    val updater: CacheUpdater,
    categoryDao: CategoryDao) extends SiteDao {

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
          .filter(_.tsversion > lift(tsversion))
      }
    }
    updateCache(apps, getCreator)
  }

  /** Returns function creates [[com.bitworks.rtb.model.db.Site Site]] */
  private def getCreator = createSite(
    ctx.run(Schema.siteCategory),
    categoryDao.getAll)(_)

  /**
    * Creates [[com.bitworks.rtb.model.db.Site Site]] from
    * [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    *
    * @param sc         all site categories from DB
    * @param categories all categories from DB
    * @param entity     [[com.bitworks.rtb.service.dao.schema.SiteEntity SiteEntity]]
    * @return created [[com.bitworks.rtb.model.db.Site Site]]
    */
  private def createSite(
      sc: Seq[SiteCategoryEntity],
      categories: Seq[IABCategory])(
      entity: SiteEntity) = {
    val status = Status(entity.status)

    val domain = entity.domain match {
      case Some(d) => d
      case _ => throw new DataValidationException(s"site id: ${entity.id}. Empty domain")
    }

    val catIds = sc.filter(_.siteId == entity.id).map(_.iabCategoryId)
    val siteCategories = categories.filter(x => catIds.contains(x.id))

    Site(
      entity.id,
      entity.name,
      status,
      entity.privacyPolicy,
      entity.test,
      domain,
      entity.keyword,
      siteCategories)
  }
}
