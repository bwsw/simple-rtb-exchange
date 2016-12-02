package com.bitworks.rtb.service

import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}
import scala.concurrent.duration._

/**
  * Test for [[com.bitworks.rtb.service.AuctionImpl AuctionImpl]].
  *
  * @author Egor Ilchenko
  */
class AuctionTest extends FlatSpec with Matchers with EasyMockSugar {


  "Auction" should "choose all bids if no conflicts" in {
    val response = BidResponseBuilder(
      "responseId",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("bidid", "impId", BigDecimal(24)).build
          )).build,
        SeatBidBuilder(
          Seq(
            BidBuilder("bid2id", "imp2Id", BigDecimal(96)).build
          )).build,
        SeatBidBuilder(
          Seq(
            BidBuilder("bid3id", "imp3Id", BigDecimal(48)).build
          )).build
      )).build

    val confMock = mock[Configuration]
    expecting {
      confMock.auctionTimeout.andStubReturn(10.seconds)
    }
    whenExecuting(confMock) {
      val auction = new AuctionImpl(confMock)
      val winners = auction.winners(Seq(response))
      winners.length shouldBe 1

      val seatBids = winners.flatMap(_.seatBid)
      seatBids.length shouldBe 3

      val bidsIds = seatBids.flatMap(_.bid).map(_.id)
      bidsIds should contain theSameElementsAs Seq("bidid", "bid2id", "bid3id")
    }
  }

  it should "return empty response if it can not find winner" in {
    val confMock = mock[Configuration]
    expecting {
      confMock.auctionTimeout.andStubReturn(10.seconds)
    }
    whenExecuting(confMock) {
      val auction = new AuctionImpl(confMock)
      val winners = auction.winners(Nil)

      winners shouldBe empty
    }
  }

  it should "return bid with higher price if conflicting" in {
    val response = BidResponseBuilder(
      "responseId",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("bidid", "impId1", BigDecimal(24)).build,
            BidBuilder("bid2id", "impId2", BigDecimal(96)).build,
            BidBuilder("bid3id", "impId3", BigDecimal(48)).build
          ))
          .build,
        SeatBidBuilder(
          Seq(BidBuilder("bid4id", "impId1", BigDecimal(40)).build))
          .build
      )).build

    val confMock = mock[Configuration]
    expecting {
      confMock.auctionTimeout.andStubReturn(10.seconds)
    }
    whenExecuting(confMock) {
      val auction = new AuctionImpl(confMock)
      val winners = auction.winners(Seq(response))
      winners.length shouldBe 1

      val seatBids = winners.flatMap(_.seatBid)
      seatBids.length shouldBe 2

      val bidsIds = seatBids.flatMap(_.bid).map(_.id)
      bidsIds should contain theSameElementsAs Seq("bid4id", "bid2id", "bid3id")
    }
  }

  it should "choose best bids from grouped conflicting seat bids" in {
    val response = BidResponseBuilder(
      "response1Id",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("1", "1", BigDecimal(10)).build,
            BidBuilder("2", "2", BigDecimal(20)).build,
            BidBuilder("3", "3", BigDecimal(30)).build))
          .withGroup(1)
          .build,
        SeatBidBuilder(
          Seq(
            BidBuilder("4", "4", BigDecimal(40)).build,
            BidBuilder("5", "5", BigDecimal(50)).build,
            BidBuilder("6", "6", BigDecimal(60)).build))
          .withGroup(1)
          .build
      )).build

    val response2 = BidResponseBuilder(
      "response2Id",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("7", "4", BigDecimal(10)).build,
            BidBuilder("8", "5", BigDecimal(20)).build,
            BidBuilder("9", "6", BigDecimal(30)).build))
          .withGroup(1)
          .build,
        SeatBidBuilder(
          Seq(
            BidBuilder("10", "1", BigDecimal(40)).build,
            BidBuilder("11", "2", BigDecimal(50)).build,
            BidBuilder("12", "3", BigDecimal(60)).build))
          .withGroup(1)
          .build
      )).build

    val confMock = mock[Configuration]
    expecting {
      confMock.auctionTimeout.andStubReturn(10.seconds)
    }
    whenExecuting(confMock) {
      val auction = new AuctionImpl(confMock)
      val winners = auction.winners(Seq(response, response2))
      winners.map(_.id) should contain theSameElementsAs Seq(response.id, response2.id)

      val seatBids = winners.flatMap(_.seatBid)
      seatBids.length shouldBe 2

      val bidsIds = seatBids.flatMap(_.bid).map(_.id)
      bidsIds should contain theSameElementsAs Seq("4", "5", "6", "10", "11", "12")
    }
  }

  it should "returns nothing if time is out" in {
    val response = BidResponseBuilder(
      "response1Id",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("1", "1", BigDecimal(10)).build,
            BidBuilder("2", "2", BigDecimal(20)).build,
            BidBuilder("3", "3", BigDecimal(30)).build))
          .withGroup(1)
          .build,
        SeatBidBuilder(
          Seq(
            BidBuilder("4", "4", BigDecimal(40)).build,
            BidBuilder("5", "5", BigDecimal(50)).build,
            BidBuilder("6", "6", BigDecimal(60)).build))
          .withGroup(1)
          .build
      )).build

    val response2 = BidResponseBuilder(
      "response2Id",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("7", "4", BigDecimal(10)).build,
            BidBuilder("8", "5", BigDecimal(20)).build,
            BidBuilder("9", "6", BigDecimal(30)).build))
          .withGroup(1)
          .build,
        SeatBidBuilder(
          Seq(
            BidBuilder("10", "1", BigDecimal(40)).build,
            BidBuilder("11", "2", BigDecimal(50)).build,
            BidBuilder("12", "3", BigDecimal(60)).build))
          .withGroup(1)
          .build
      )).build

    val confMock = mock[Configuration]
    expecting {
      confMock.auctionTimeout.andStubReturn(0.second)
    }
    whenExecuting(confMock) {
      val auction = new AuctionImpl(confMock)
      val winners = auction.winners(Seq(response, response2))
      winners shouldBe empty
    }
  }
}
