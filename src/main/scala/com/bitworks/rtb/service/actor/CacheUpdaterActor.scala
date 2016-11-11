package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging}
import com.bitworks.rtb.model.message._
import com.bitworks.rtb.service.dao.CacheUpdater

/**
  * Actor for cache updating.
  *
  * @author Egor Ilchenko
  */
class CacheUpdaterActor(dbUpdater: CacheUpdater) extends Actor with ActorLogging {
  override def receive = {
    case InitCache =>
      log.info("cache initializing started...")
      dbUpdater.notifyAll(InitCache)
      log.info("cache initializing finished")
    case UpdateCache =>
      log.info("cache updating started...")
      dbUpdater.notifyAll(UpdateCache)
      log.info("cache updating finished")
    case _ =>
  }
}
