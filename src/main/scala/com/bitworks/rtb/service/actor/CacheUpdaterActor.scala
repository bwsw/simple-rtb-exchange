package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging}
import com.bitworks.rtb.model.message._
import com.bitworks.rtb.service.dao.CacheUpdater

/**
  * Actor for cache updating.
  *
  * @author Egor Ilchenko
  */
class CacheUpdaterActor(dbUpdater: CacheUpdater) extends Actor with ActorLogging{
  override def receive = {
    case InitCache =>
      log.debug("cache initializing started...")
      dbUpdater.notifyAll(InitCache)
      log.debug("cache initializing finished")
    case UpdateCache =>
      log.debug("cache updating started...")
      dbUpdater.notifyAll(UpdateCache)
      log.debug("cache updating finished")
    case _ =>
  }
}
