package com.bitworks.rtb.service

import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import org.scalatest.{FlatSpec, Matchers}

/**
  *
  *
  * @author Egor Ilchenko
  */
class AuctionTest extends FlatSpec with Matchers {

  "Auction" should "choose best bid in trivial example" in {
    val auction = new AuctionImpl

    val response = BidResponseBuilder(
      "asd",
      Seq(
        SeatBidBuilder(Seq(
          BidBuilder("bidid", "impId", BigDecimal(24)).build,
          BidBuilder("bid2id", "impId", BigDecimal(96)).build,
          BidBuilder("bid3id", "impId", BigDecimal(48)).build
        )).build
      )).build

    val winner = auction.winner(Seq(response))

    winner match {
      case None => fail
      case Some(w) => w.seatBid.head.bid.head.id shouldBe "bid2id"
    }
  }

}
