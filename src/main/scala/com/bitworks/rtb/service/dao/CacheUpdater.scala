package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.message.{CacheMessage, InitCache}

/**
  * Stores DAO services and performs cache initialization and updating.
  *
  * @author Egor Ilchenko
  */
class CacheUpdater {
  private var daoServices: List[BaseDao[_]] = List.empty

  /**
    * Attaches DAO service to CacheUpdater
    *
    * @param dao DAO Service
    */
  def attach(dao: BaseDao[_]) = daoServices ::= dao


  /**
    * Notifies all attached DAO services
    *
    * @param action action to notify with
    */
  def notifyAll(action: CacheMessage) = daoServices.foreach(_.notify(action))

}

