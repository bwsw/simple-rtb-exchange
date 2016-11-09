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

  bind[DbContext] toNonLazy new DbContext("db")
  bind[CacheUpdater] toNonLazy new CacheUpdater
  bind[CategoryDao] toNonLazy injected[CategoryDaoImpl]
  bind[PublisherDao] toNonLazy injected[PublisherDaoImpl]
  bind[BidderDao] toNonLazy injected[BidderDaoImpl]
  bind[AppDao] toNonLazy injected[AppDaoImpl]
  bind[SiteDao] toNonLazy injected[SiteDaoImpl]

  bind[ActorSystem] toNonLazy ActorSystem("rtb")
  bind[CacheUpdaterActor] toProvider injected[CacheUpdaterActor]

  bind[Configuration] toNonLazy new Configuration
}
