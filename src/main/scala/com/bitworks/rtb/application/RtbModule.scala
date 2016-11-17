package com.bitworks.rtb.application

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import com.bitworks.rtb.service.actor._
import com.bitworks.rtb.service.dao._
import com.bitworks.rtb.service.factory._
import com.bitworks.rtb.service.parser.{AdRequestJsonParser, AdRequestParser,
BidResponseJsonParser, BidResponseParser}
import com.bitworks.rtb.service.writer.{AdResponseJsonWriter, AdResponseWriter,
BidRequestJsonWriter, BidRequestWriter}
import com.bitworks.rtb.service._
import scaldi.Module

/**
  * Module defines DI bindings.
  *
  * @author Egor Ilchenko
  */
class RtbModule extends Module {
  bind[Configuration] toNonLazy new Configuration

  bind[DbContext] toNonLazy new DbContext("db")
  bind[CacheUpdater] toNonLazy new CacheUpdater
  bind[CategoryDao] toNonLazy injected[CategoryDaoImpl]
  bind[PublisherDao] toNonLazy injected[PublisherDaoImpl]
  bind[BidderDao] toNonLazy injected[BidderDaoImpl]
  bind[AppDao] toNonLazy injected[AppDaoImpl]
  bind[SiteDao] toNonLazy injected[SiteDaoImpl]

  implicit val actorSystem = ActorSystem("rtb")
  implicit val materializer = ActorMaterializer()

  bind[ActorSystem] toNonLazy actorSystem
  bind[Materializer] toNonLazy materializer
  bind[RequestHandler] toNonLazy injected[RequestHandler]
  bind[CacheUpdaterActor] toProvider injected[CacheUpdaterActor]

  bind[RequestMaker] toNonLazy injected[AkkaHttpRequestMaker]


  bind[AdRequestParser] toNonLazy injected[AdRequestJsonParser]
  bind[AdResponseWriter] toNonLazy injected[AdResponseJsonWriter]
  bind[BidRequestWriter] toNonLazy injected[BidRequestJsonWriter]
  bind[BidResponseParser] toNonLazy injected[BidResponseJsonParser]
  bind[BidRequestFactory] toNonLazy injected[BidRequestFactoryImpl]
  bind[AdResponseFactory] toNonLazy injected[AdResponseFactoryImpl]
  bind[Auction] toNonLazy injected[AuctionImpl]

  bind[BidRequestMaker] toNonLazy injected[BidRequestMakerImpl]

  bind[BidActor] toProvider new BidActor
  bind[WinActor] toProvider new WinActor
}
