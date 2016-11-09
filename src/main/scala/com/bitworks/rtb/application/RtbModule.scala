package com.bitworks.rtb.application

import akka.actor.ActorSystem
import com.bitworks.rtb.service.Configuration
import com.bitworks.rtb.service.actor.CacheUpdaterActor
import com.bitworks.rtb.service.dao._
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
  bind[DisplayManagerDao] to injected[DisplayManagerDaoImpl]
  bind[AppDao] to injected[AppDaoImpl]
  bind[SiteDao] to injected[SiteDaoImpl]

  bind[ActorSystem] to ActorSystem("rtb")
  bind[CacheUpdaterActor] toProvider injected[CacheUpdaterActor]

  bind[Configuration] to new Configuration
}
