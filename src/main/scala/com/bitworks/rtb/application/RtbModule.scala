package com.bitworks.rtb.application

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import com.bitworks.rtb.model.http.{ContentTypeModel, Json, Unknown}
import com.bitworks.rtb.service.actor._
import com.bitworks.rtb.service.dao._
import com.bitworks.rtb.service.factory._
import com.bitworks.rtb.service.parser._
import com.bitworks.rtb.service.writer._
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

  bind[HttpRequestMaker] toNonLazy injected[AkkaHttpRequestMaker]

  bindAdModelConverters
  bindBidModelConverters

  bind[BidRequestFactory] toNonLazy injected[BidRequestFactoryImpl]
  bind[AdResponseFactory] toNonLazy injected[AdResponseFactoryImpl]

  bind[Auction] toNonLazy injected[AuctionImpl]

  bind[BidRequestMaker] toNonLazy injected[BidRequestMakerImpl]
  bind[WinNoticeRequestMaker] toNonLazy injected[WinNoticeRequestMakerImpl]

  bind[BidActor] toProvider new BidActor
  bind[WinActor] toProvider new WinActor

  def bindAdModelConverters = {
    bind[Map[ContentTypeModel, AdRequestParser]] toNonLazy {
      val map: Map[ContentTypeModel, AdRequestParser] =
        Map(Json -> injected[AdRequestJsonParser])
      map
    }
    bind[Map[ContentTypeModel, AdResponseWriter]] toNonLazy {
      val map: Map[ContentTypeModel, AdResponseWriter] =
        Map(Json -> injected[AdResponseJsonWriter])
      map
    }

    bind[AdModelsConverter] toNonLazy injected[AdModelsConverterImpl]
  }

  def bindBidModelConverters = {
    bind[Map[ContentTypeModel, BidResponseParser]] toNonLazy {
      val parser = injected[BidResponseJsonParser]
      val map: Map[ContentTypeModel, BidResponseParser] =
        Map(Json -> parser, Unknown -> parser)
      map
    }
    bind[Map[ContentTypeModel, BidRequestWriter]] toNonLazy {
      val writer = injected[BidRequestJsonWriter]
      val map: Map[ContentTypeModel, BidRequestWriter] =
        Map(Json -> writer, Unknown -> writer)
      map
    }

    bind[BidModelsConverter] toNonLazy injected[BidModelsConverterImpl]
  }
}
