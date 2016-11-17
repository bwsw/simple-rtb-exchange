package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.bitworks.rtb.model.message.{BidRequestFail, BidRequestSuccess, SendBidRequest}
import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import com.bitworks.rtb.service.{BidRequestMaker, Configuration}
import scaldi.Injector
import scaldi.Injectable._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Actor responsible for sending requests to bidders.
  *
  * @author Egor Ilchenko
  */
class BidActor(implicit injector: Injector) extends Actor with ActorLogging {

  val requestMaker = inject[BidRequestMaker]
  val configuration = inject[Configuration]

  val timeout = configuration.bidRequestTimeout

  override def receive: Receive = {
    case SendBidRequest(bidder, req) =>
      try {
        val response = Await.result(requestMaker.send(bidder, req), timeout)
        sender ! BidRequestSuccess(response)
      } catch {
        case e: Throwable => sender ! BidRequestFail(e.getMessage)
      }

  }
}

object BidActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.BidActor BidActor]]. */
  def props(implicit inj: Injector) = {
    Props(new BidActor)
  }
}
