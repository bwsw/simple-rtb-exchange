package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.bitworks.rtb.application.RequestContextWrapper
import com.bitworks.rtb.model.ad.response.builder.{AdResponseBuilder, ImpBuilder}
import com.bitworks.rtb.model.ad.response.{AdResponse, Error}
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message.{BidRequestResult, _}
import com.bitworks.rtb.service.Auction
import com.bitworks.rtb.service.dao.BidderDao
import com.bitworks.rtb.service.factory.BidRequestFactory
import com.bitworks.rtb.service.parser.AdRequestParser
import com.bitworks.rtb.service.writer.AdResponseWriter
import scaldi.Injector
import scaldi.akka.AkkaInjectable._

import scala.collection.mutable.ListBuffer


/**
  * Main actor processing ad requests.
  *
  * @author Egor Ilchenko
  */
class RequestActor(
    bidActor: ActorRef,
    winActor: ActorRef,
    ctx: RequestContextWrapper)(
    implicit inj: Injector) extends Actor with ActorLogging {

  val writer = inject[AdResponseWriter]
  val parser = inject[AdRequestParser]
  val factory = inject[BidRequestFactory]
  val auction = inject[Auction]
  val bidderDao = inject[BidderDao]

  val bidders = bidderDao.getAll
  val receivedBidResponses = new ListBuffer[BidRequestResult]

  override def receive: Receive = {

    case HandleRequest(bytes) =>
      try {
        log.debug("started request handling")
        val adRequest = parser.parse(bytes)
        val bidRequest = factory.create(adRequest)

        bidderDao.getAll match {
          case Seq() => self ! ValidationError("bidders not found")
          case s: Seq[Bidder] =>
            s.foreach { bidder =>
              bidActor ! SendBidRequest(bidder, bidRequest)
            }
        }
      }
      catch {
        case e: Throwable => self ! ValidationError(e.toString)
      }

    case msg: BidRequestResult =>
      log.debug(s"bid response received: $msg")
      receivedBidResponses.append(msg)
      if (receivedBidResponses.size == bidders.length)
        self ! StartAuction

    case StartAuction =>
      log.debug("auction started")
      val successful = receivedBidResponses
        .collect {
          case BidRequestSucess(r) => r
        }
      log.debug(s"auction participants: ${successful.length}")

      val winner = auction.winner(successful)
      log.debug(s"auction winner: $winner")

      winner match {
        case Some(r) => winActor ! r
        case None => self ! ValidationError("winner not defined")
      }

    case AdMarkup(ad) =>
      log.debug("ad markup received")

      val response = AdResponseBuilder("123")
        .withImp(Seq(ImpBuilder("1", ad, 1).build))
        .build

      self ! response


    case ValidationError(msg) =>
      log.debug(s"error occured: $msg")

      val response = AdResponseBuilder("123")
        .withError(Error(123, msg))
        .build

      self ! response

    case response: AdResponse =>
      val bytes = writer.write(response)
      ctx.complete(bytes)

  }
}

object RequestActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.RequestActor RequestActor]] */
  def props(
      bidActor: ActorRef,
      winActor: ActorRef,
      ctx: RequestContextWrapper)()(implicit inj: Injector) = {
    Props(new RequestActor(bidActor, winActor, ctx))
  }
}
