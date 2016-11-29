package com.bitworks.rtb.service

import akka.actor.ActorSystem
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.http.{HttpRequestModel, POST}
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.response.BidResponse
import com.bitworks.rtb.service.factory.BidModelsConverter

import scala.concurrent.Future

/**
  * Bid requests sender.
  *
  * @author Egor Ilchenko
  */
trait BidRequestMaker {

  /**
    * Sends bid request to bidder.
    *
    * @param bidder  [[com.bitworks.rtb.model.db.Bidder Bidder]]
    * @param request [[com.bitworks.rtb.model.request.BidRequest BidRequest]]
    * @return Future [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    */
  def send(bidder: Bidder, request: BidRequest): Future[BidResponse]
}

/**
  * Bid requests sender implementation.
  *
  * @author Egor Ilchenko
  */
class BidRequestMakerImpl(
    config: Configuration,
    bidConverter: BidModelsConverter,
    httpRequestMaker: HttpRequestMaker,
    system: ActorSystem) extends BidRequestMaker {

  import system.dispatcher

  override def send(bidder: Bidder, request: BidRequest) = {
    val bytes = bidConverter.write(request, config.bidRequestContentType)

    val requestModel = HttpRequestModel(bidder.endpoint, POST, Some(bytes))
    httpRequestMaker.make(requestModel) map { response =>
      bidConverter.parse(response.body, response.contentType)
    }
  }
}
