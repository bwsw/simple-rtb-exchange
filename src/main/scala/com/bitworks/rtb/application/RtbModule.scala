package com.bitworks.rtb.application

import akka.actor.ActorSystem
import com.bitworks.rtb.service.actor._
import com.bitworks.rtb.service.dao._
import com.bitworks.rtb.service.factory._
import com.bitworks.rtb.service.parser.{AdRequestJsonParser, AdRequestParser}
import com.bitworks.rtb.service.writer.{AdResponseJsonWriter, AdResponseWriter}
import com.bitworks.rtb.service.{Auction, AuctionImpl, Configuration}
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

  bind[AdRequestParser] toNonLazy injected[AdRequestJsonParser]
  bind[AdResponseWriter] toNonLazy injected[AdResponseJsonWriter]
  bind[BidRequestFactory] toNonLazy injected[BidRequestFactoryImpl]
  bind[AdResponseFactory] toNonLazy injected[AdResponseFactoryImpl]
  bind[Auction] toNonLazy injected[AuctionImpl]

  bind[RequestHandler] toNonLazy injected[RequestHandler]
}
