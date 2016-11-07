package com.bitworks.rtb.service.dao

import com.bitworks.rtb.service.dao.schema.EntityMetaInfo
import com.bitworks.rtb.model.message.CacheMessage


/**
  * Base DAO service.
  *
  * @author Egor Ilchenko
  */
trait BaseDao[E] {
  updater.attach(this)

  protected val updater: CacheUpdater
  protected var tsversion: Long = 0


  /**
    * Receives message and perform corresponding action
    *
    * @param action action to perform
    */
  def notify(action: CacheMessage): Unit

  /**
    * Returns entity by ID or None, if not found
    *
    * @param id ID of entity.
    */
  def get(id: Int): Option[E]

  /** Returns all entities  */
  def getAll: Seq[E]


  /**
    * Updates stored tsversion.
    *
    * @param entities entities with meta information from DB.
    * @tparam T concrete entity type
    */
  protected def updateTsVersion[T <: EntityMetaInfo](entities: Seq[T]) = entities match {
    case Seq() =>
    case _ => tsversion = entities.maxBy(x => x.tsversion).tsversion
  }

}

