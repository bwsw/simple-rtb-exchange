package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.BaseEntity
import com.bitworks.rtb.service.Logging
import com.bitworks.rtb.service.dao.schema.EntityMetaInfo

/**
  * Provides "entity by ID" cache.
  *
  * @author Egor Ilchenko
  */
trait CacheHelper[E] extends BaseDao[E] with Logging{

  private var byIdCache: Map[Int, E] = Map.empty

  /**
    * Updates cache using actual data from DB
    *
    * @param entities entities with meta information from DB
    * @param f        function creates some DTO from DB entity or None, if
    *                 creation is impossible
    * @tparam T concrete entity type
    */
  protected def updateCache[T <: EntityMetaInfo with BaseEntity](
      entities: Seq[T],
      f: T => Option[E]) = {

    log.info(s"cache updating started. DB entities count: ${entities.length}")

    entities.foreach { x =>
      if (x.deleted) {
        byIdCache = byIdCache - x.id
      }
      else {
        f(x) match {
          case Some(v) => byIdCache = byIdCache + (x.id -> v)
          case None =>
        }
      }
      tsversion = math.max(tsversion, x.tsversion)
    }

    log.info(s"cache updating finished. Cache entities count: ${byIdCache.size}, " +
      s"latest tsversion: $tsversion")
  }

  override def get(id: Int): Option[E] = {
    byIdCache.get(id)
  }

  override def getAll: Seq[E] = {
    byIdCache.values.toSeq
  }

  override def get(ids: Seq[Int]) = {
    byIdCache.filterKeys(ids.contains(_)).values.toSeq
  }
}
