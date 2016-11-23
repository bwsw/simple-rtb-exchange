package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.RoundRobinPool
import akka.stream.ActorMaterializer
import com.bitworks.rtb.application.HttpRequestWrapper
import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.ad.response.{AdResponse, Error}
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message.{BidRequestResult, _}
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

  var auctionStarted = false

  val bidActorProps = injectActorProps[BidActor]
  val bidRouter = context.actorOf(
    RoundRobinPool(bidders.length)
      .props(bidActorProps), "bidrouter")

  val winActor = injectActorRef[WinActor]

  var adRequest: Option[AdRequest] = None

  override def receive: Receive = {

    case HandleRequest =>
      log.debug("started request handling")

      request.inner.entity.toStrict(configuration.toStrictTimeout) map {
        entity =>
          val bytes = entity.data.toArray
          adRequest = Some(parser.parse(bytes))

          val bidRequest = factory.create(adRequest.get)
          if (bidRequest.isEmpty) {
            bidders match {
              case Seq() => onError("bidders not found")
              case _: Seq[Bidder] =>
                bidders.foreach { bidder =>
                  bidRouter ! SendBidRequest(bidder, bidRequest.get)
                }

                context.system.scheduler.scheduleOnce(
                  configuration.bidRequestTimeout,
                  self,
                  StartAuction)
            }
          } else {
            onError("Bid request can not be constructed.")
          }

      } onFailure {
        case exc => onError(exc.toString)
      }

    case msg: BidRequestResult =>
      log.debug(s"bid response received: $msg")
      receivedBidResponses.append(msg)
      if (receivedBidResponses.size == bidders.length)
        self ! StartAuction

    case msg: BidResponse =>
      log.debug("bid response received")
      adRequest match {
        case Some(ar) =>
          try {
            val response = adResponseFactory.create(ar, msg)
            completeRequest(response)
          } catch {
            case e: Throwable => onError(e.getMessage)
          }
        case None =>
          log.error("ad request is not defined")
          request.fail()
      }

    case StartAuction =>
      if (!auctionStarted) {
        auctionStarted = true
        log.debug("auction started")
        val successful = receivedBidResponses
          .collect {
            case BidRequestSuccess(response) => response
          }
        log.debug(s"auction participants: ${successful.length}")
        val winner = auction.winner(successful)
        log.debug(s"auction winner: $winner")

        winner match {
          case Some(response) => winActor ! response
          case None => onError("winner not defined")
        }
      }

  }

  /**
    * Completes request with ad response.
    *
    * @param response [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]]
    */
  def completeRequest(response: AdResponse) = {
    log.debug("completing request...")
    val bytes = writer.write(response)
    request.complete(bytes)
  }

  /**
    * Completes request with unsuccessful ad response.
    *
    * @param msg error message
    */
  def onError(msg: String) = {
    log.debug(s"an error occurred: $msg")

    adRequest match {
      case Some(ar) =>
        val response = adResponseFactory.create(ar, Error(123, msg))
        completeRequest(response)
      case None =>
        log.error("ad request is not defined")
        request.fail()
    }
  }
}

object RequestActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.RequestActor RequestActor]]. */
  def props(
      wrapper: HttpRequestWrapper)()(implicit inj: Injector) = {
    Props(new RequestActor(wrapper))
  }
}
