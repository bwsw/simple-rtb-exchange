package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.DisplayManager
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}
import com.bitworks.rtb.service.dao.schema.DisplayManagerEntity

/**
  * DAO for [[com.bitworks.rtb.model.db.DisplayManager DisplayManager]].
  *
  * @author Egor Ilchenko
  */
trait DisplayManagerDao extends BaseDao[DisplayManager] with CacheHelper[DisplayManager] {

  /** Returns display managers by owner(site or app) ID
    *
    * @param id site or app ID
    * @return associated display managers
    */
  def getByOwnerId(id: Int): Seq[DisplayManager]
}

/**
  * DAO implementation for [[com.bitworks.rtb.model.db.DisplayManager DisplayManager]].
  *
  * @param ctx     DB context
  * @param updater [[CacheUpdater CacheUpdater]]
  */
class DisplayManagerDaoImpl(
    ctx: DbContext,
    val updater: CacheUpdater) extends DisplayManagerDao {

  import ctx._

  private var byOwnerIdCache: Map[Int, Seq[DisplayManager]] = Map.empty

  override def notify(action: CacheMessage) = {
    val managers = action match {
      case InitCache => ctx.run {
        Schema.displayManager
          .filter(!_.deleted)
      }
      case UpdateCache => ctx.run {
        Schema.displayManager
          .filter(_.tsversion > lift(tsversion))
      }
    }
    updateCache(managers, createDisplayManager)
    updateOwnerIdCache()
  }

  override def getByOwnerId(id: Int) = {
    byOwnerIdCache.get(id) match {
      case Some(managers) => managers
      case None => Seq.empty[DisplayManager]
    }
  }

  /** Updates display managers by owner ID cache */
  private def updateOwnerIdCache() = {
    val siteDisplayManages = ctx.run(Schema.siteDisplayManager)

    byOwnerIdCache = siteDisplayManages
      .groupBy(_.siteId).map {
      case (id, managers) =>
        (id, managers.map(x => get(x.displayManagerId).orNull).filter(_ != null))
    }
  }

  /**
    * Creates [[com.bitworks.rtb.model.db.DisplayManager DisplayManager]] from
    * [[com.bitworks.rtb.service.dao.schema.DisplayManagerEntity DisplayManagerEntity]]
    *
    * @param entity [[com.bitworks.rtb.service.dao.schema.DisplayManagerEntity
    *               DisplayManagerEntity]]
    * @return created [[com.bitworks.rtb.model.db.DisplayManager DisplayManager]]
    */
  private def createDisplayManager(entity: DisplayManagerEntity) = {
    Some(DisplayManager(entity.id, entity.name, entity.ver))
  }
}
