package com.bitworks.rtb.application

import akka.actor.ActorSystem
import com.bitworks.rtb.service.actor.CacheUpdaterActor
import com.bitworks.rtb.service.dao.entitydao._
import com.bitworks.rtb.service.dao.{CacheUpdater, DbContext}
import scaldi.Module

/**
  * Module defines DI bindings.
  *
  * @author Egor Ilchenko
  */
class RtbModule extends Module {

  bind[DbContext] to new DbContext("db")
  bind[CacheUpdater] to new CacheUpdater
  bind[CategoryDao] to injected[CategoryDaoImpl]
  bind[PublisherDao] to injected[PublisherDaoImpl]
  bind[BidderDao] to injected[BidderDaoImpl]

  bind[ActorSystem] to ActorSystem("rtb")
  binding toProvider injected[CacheUpdaterActor]

  bind[Configuration] to new Configuration
}
