package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.message.CacheMessage

/**
  * Base DAO service.
  *
  * @author Egor Ilchenko
  */
trait BaseDao[E] {
  updater.register(this)

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
    * Returns entities by IDs list
    *
    * @param ids list of entities IDs
    */
  def get(ids: Seq[Int]): Seq[E]
}

