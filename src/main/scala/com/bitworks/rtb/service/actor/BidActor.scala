package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.bitworks.rtb.model.message.{BidRequestSucess, SendBidRequest}
import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import scaldi.Injector

/**
  * Actor responsible for sending requests to bidders.
  *
  * @author Egor Ilchenko
  */
class BidActor extends Actor with ActorLogging {
  override def receive: Receive = {

    case SendBidRequest(bidder, req) =>
      val bid = BidBuilder("bidId", "impId", BigDecimal("123")).build
      val seatBid = SeatBidBuilder(Seq(bid)).build
      val bidResponse = BidResponseBuilder("respid", Seq(seatBid)).build
      sender ! BidRequestSucess(bidResponse)

  }
}

object BidActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.BidActor BidActor]] */
  def props(implicit inj: Injector) = {
    Props(new BidActor)
  }
}
