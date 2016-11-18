package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.RoundRobinPool
import akka.stream.ActorMaterializer
import com.bitworks.rtb.application.HttpRequestWrapper
import com.bitworks.rtb.model.ad.response.{AdResponse, Error}
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message.{BidRequestResult, _}
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.response.BidResponse
import com.bitworks.rtb.service.dao.BidderDao
import com.bitworks.rtb.service.factory.{AdResponseFactory, BidRequestFactory}
import com.bitworks.rtb.service.parser.AdRequestParser
import com.bitworks.rtb.service.writer.AdResponseWriter
import com.bitworks.rtb.service.{Auction, Configuration}
import scaldi.Injector
import scaldi.akka.AkkaInjectable._

import scala.collection.mutable.ListBuffer

/**
  * Main actor to process ad requests.
  *
  * @author Egor Ilchenko
  */
class RequestActor(
    request: HttpRequestWrapper)(
    implicit inj: Injector) extends Actor with ActorLogging {

  import context.dispatcher

  implicit val materializer = ActorMaterializer()
  val configuration = inject[Configuration]
  val writer = inject[AdResponseWriter]
  val parser = inject[AdRequestParser]
  val factory = inject[BidRequestFactory]
  val auction = inject[Auction]
  val bidderDao = inject[BidderDao]
  val adResponseFactory = inject[AdResponseFactory]

  val bidders = bidderDao.getAll
  val receivedBidResponses = new ListBuffer[BidRequestResult]

  val bidActorProps = injectActorProps[BidActor]
  val bidRouter = context.actorOf(
    RoundRobinPool(bidders.length)
      .props(bidActorProps), "bidrouter")

  val winActor = injectActorRef[WinActor]

  var bidRequest: Option[BidRequest] = None

  override def receive: Receive = {

    case HandleRequest =>
      log.debug("started request handling")

      request.inner.entity.toStrict(configuration.toStrictTimeout) map {
        entity =>
          val bytes = entity.data.toArray
          val adRequest = parser.parse(bytes)
          bidRequest = Some(factory.create(adRequest))

          bidderDao.getAll match {
            case Seq() => onError("bidders not found")
            case bidders: Seq[Bidder] =>
              bidders.foreach { bidder =>
                bidRouter ! SendBidRequest(bidder, bidRequest.get)
              }
          }
      } onFailure {
        case exc => onError(exc.toString)
      }

    case msg: BidRequestResult =>
      log.debug(s"bid response received: $msg")
      receivedBidResponses.append(msg)
      if (receivedBidResponses.size == bidders.length)
        startAuction()

    case msg: BidResponse =>
      log.debug(s"bid response received $msg")

      val response = adResponseFactory.create(msg)

      completeRequest(response)

  }

  def startAuction() = {
    log.debug("auction started")
    val successful = receivedBidResponses
      .collect {
        case BidRequestSuccess(response) => response
      }
    log.debug(s"auction participants: ${successful.length}")

    val winner = auction.winner(successful)

    log.debug(s"auction winner: $winner")

    winner match {
      case Some(response) =>
        log.debug(s"winner seatBids: ${response.seatBid.length}")
        log.debug(s"winner bids; ${response.seatBid.flatMap(_.bid).length}")
        winActor ! SendWinNotice(bidRequest.get, response)
      case None => onError("winner not defined")
    }
  }

  def completeRequest(response: AdResponse) = {
    log.debug("completing request...")
    val bytes = writer.write(response)
    request.complete(bytes)
  }

  def onError(msg: String) = {
    log.debug(s"an error occurred: $msg")

    val response = adResponseFactory.create(Error(123, msg))

    completeRequest(response)
  }
}

object RequestActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.RequestActor RequestActor]]. */
  def props(
      wrapper: HttpRequestWrapper)()(implicit inj: Injector) = {
    Props(new RequestActor(wrapper))
  }
}
