package com.bitworks.rtb.service.dao.entitydao

import com.bitworks.rtb.service.dao.{BaseDao, CacheHelper, CacheUpdater, DbContext}
import com.bitworks.rtb.service.dao.schema.IABCategoryEntity
import com.bitworks.rtb.model.db.IABCategory
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}
import io.getquill._

/**
  * DAO for [[com.bitworks.rtb.model.db.IABCategory IABCategory]].
  *
  * @author Egor Ilchenko
  */
trait CategoryDao extends BaseDao[IABCategory] with CacheHelper[IABCategory]

/**
  * DAO implementation for [[com.bitworks.rtb.model.db.IABCategory IABCategory]].
  *
  * @param ctx     DB context
  * @param updater [[CacheUpdater CacheUpdater]]
  */
class CategoryDaoImpl(ctx: DbContext, val updater: CacheUpdater) extends CategoryDao {
  import ctx._


  override def notify(action: CacheMessage) = {
    val categories = action match {
      case InitCache => ctx.run {
        Schema.iabCategory
          .filter(!_.deleted)
      }
      case UpdateCache => ctx.run {
        Schema.iabCategory
          .filter(_.tsversion > lift(tsversion))
      }
    }
    updateCache(categories, mapCategory)
  }

  /**
    * Creates [[com.bitworks.rtb.model.db.IABCategory IABCategory]] from
    * [[com.bitworks.rtb.service.dao.schema.IABCategoryEntity IABCategoryEntity]]
    *
    * @param entity [[com.bitworks.rtb.service.dao.schema.IABCategoryEntity IABCategoryEntity]]
    * @return created [[com.bitworks.rtb.model.db.IABCategory IABCategory]]
    */
  private def mapCategory(entity: IABCategoryEntity) = {
    IABCategory(entity.id, entity.iabId, entity.name, entity.parentId)
  }
}
