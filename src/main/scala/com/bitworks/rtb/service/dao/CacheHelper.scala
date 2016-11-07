package com.bitworks.rtb.service.dao

import com.bitworks.rtb.service.dao.schema.EntityMetaInfo
import com.bitworks.rtb.model.db.BaseEntity

/**
  * Provides "entity by ID" cache.
  *
  * @author Egor Ilchenko
  */
trait CacheHelper[E] extends BaseDao[E] {

  protected var byIdCache: Map[Int, E] = Map.empty


  /**
    * Updates cache using actual data from DB
    *
    * @param entities entities with meta information from DB
    * @param f        function creates DTO from DB entity
    * @tparam T concrete entity type
    */
  def updateCache[T <: EntityMetaInfo with BaseEntity](entities: Seq[T], f: T => E) = {
    updateTsVersion(entities)

    val deleted = entities
      .filter(_.deleted)
      .map(_.id)

    val updated = entities
      .filter(!_.deleted)
      .map(x => x.id -> f(x))

    byIdCache = byIdCache -- deleted ++ updated
  }

  override def get(id: Int): Option[E] = byIdCache.get(id)

  override def getAll: Seq[E] = byIdCache.values.toSeq
}
