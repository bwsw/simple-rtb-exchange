package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.stream.ActorMaterializer
import com.bitworks.rtb.application.HttpRequestWrapper
import com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder
import com.bitworks.rtb.model.ad.response.{AdResponse, Error}
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message.{BidRequestResult, _}
import com.bitworks.rtb.model.response.BidResponse
import com.bitworks.rtb.service.Auction
import com.bitworks.rtb.service.dao.BidderDao
import com.bitworks.rtb.service.factory.{AdResponseFactory, BidRequestFactory}
import com.bitworks.rtb.service.parser.AdRequestParser
import com.bitworks.rtb.service.writer.AdResponseWriter
import scaldi.Injector
import scaldi.akka.AkkaInjectable._

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._
import scala.util.{Failure, Success}


/**
  * Main actor processing ad requests.
  *
  * @author Egor Ilchenko
  */
class RequestActor(
    bidActor: ActorRef,
    winActor: ActorRef,
    request: HttpRequestWrapper)(
    implicit inj: Injector) extends Actor with ActorLogging {

  import context.dispatcher

  implicit val materializer = ActorMaterializer()
  val writer = inject[AdResponseWriter]
  val parser = inject[AdRequestParser]
  val factory = inject[BidRequestFactory]
  val auction = inject[Auction]
  val bidderDao = inject[BidderDao]
  val adFactory = inject[AdResponseFactory]

  val bidders = bidderDao.getAll
  val receivedBidResponses = new ListBuffer[BidRequestResult]

  override def receive: Receive = {

    case HandleRequest =>
      log.debug("started request handling")
      val timeout = 1.second
      request.inner.entity.toStrict(timeout) map {
        entity =>
          val bytes = entity.data.toArray
          val adRequest = parser.parse(bytes)
          val bidRequest = factory.create(adRequest)

          bidderDao.getAll match {
            case Seq() => onError("bidders not found")
            case s: Seq[Bidder] =>
              s.foreach { bidder =>
                bidActor ! SendBidRequest(bidder, bidRequest)
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
      log.debug("bid response received")

      val response = adFactory.create(msg)

      completeRequest(response)

  }

  def startAuction() = {
    log.debug("auction started")
    val successful = receivedBidResponses
      .collect {
        case BidRequestSuccess(r) => r
      }
    log.debug(s"auction participants: ${successful.length}")

    val winner = auction.winner(successful)
    log.debug(s"auction winner: $winner")

    winner match {
      case Some(r) => winActor ! r
      case None => onError("winner not defined")
    }
  }

  def completeRequest(response: AdResponse) = {
    val bytes = writer.write(response)
    request.complete(bytes)
  }

  def onError(msg: String) = {
    log.debug(s"error occured: $msg")

    val response = adFactory.create(Error(123, msg))

    completeRequest(response)
  }
}

object RequestActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.RequestActor RequestActor]] */
  def props(
      bidActor: ActorRef,
      winActor: ActorRef,
      wrapper: HttpRequestWrapper)()(implicit inj: Injector) = {
    Props(new RequestActor(bidActor, winActor, wrapper))
  }
}
