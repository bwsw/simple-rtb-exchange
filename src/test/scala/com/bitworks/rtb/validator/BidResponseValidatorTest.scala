package com.bitworks.rtb.validator

import com.bitworks.rtb.model.response.builder._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.validator.BidResponseValidator BidResponseValidator]]
  *
  * @author Pavel Tomskikh
  */
class BidResponseValidatorTest extends FlatSpec with Matchers {

  val validator = new BidResponseValidator

  "BidResponseValidator" should "validate correct BidResponse" in {
    val bid = BidBuilder("id", "impid", BigDecimal(42.42))
      .withAdId("adid")
      .withNurl("nurl")
      .withAdm("adm")
      .withAdomain(Seq("adomain"))
      .withBundle("bundle")
      .withIurl("iurl")
      .withCid("cid")
      .withCrid("crid")
      .withCat(Seq.empty)
      .withAttr(Set(1))
      .withDealId("dealid")
      .withH(42)
      .withW(12)
      .build

    val seatBid = SeatBidBuilder(Seq(bid))
      .withSeat("seat")
      .withGroup(1)
      .build

    val bidResponse = BidResponseBuilder("123", Seq(seatBid))
      .withBidId("456")
      .withCur("EUR")
      .withCustomData("custom")
      .build

    validator.validate(bidResponse) shouldBe true
  }

  it should "validate correct not bidded BidResponse" in {
    val bidResponse = BidResponseBuilder("123", Seq.empty)
      .withNbr(1)
      .build

    validator.validate(bidResponse) shouldBe true
  }

  val correctBid = BidBuilder("123", "456", BigDecimal(42.42)).build
  val correctSeatBid = SeatBidBuilder(Seq(correctBid)).build

  it should "validate correct BidResponse with default values" in {
    val bidResponse = BidResponseBuilder("123", Seq(correctSeatBid)).build

    validator.validate(bidResponse) shouldBe true
  }

  it should "not validate BidResponse with empty id" in {
    val incorrectBidResponse = BidResponseBuilder("", Seq(correctSeatBid)).build

    validator.validate(incorrectBidResponse) shouldBe false
  }

  it should "not validate BidResponse without seatBids and nbr" in {
    val incorrectBidResponse = BidResponseBuilder("123", Seq.empty).build

    validator.validate(incorrectBidResponse) shouldBe false
  }

  it should "not validate BidResponse without Bids in SeatBid" in {
    val incorrectSeatBid = SeatBidBuilder(Seq.empty).build
    val incorrectBidResponse = BidResponseBuilder("123", Seq(incorrectSeatBid)).build

    validator.validate(incorrectBidResponse) shouldBe false
  }

  it should "not validate BidResponse with incorrect group in SeatBid" in {
    val incorrectSeatBid = SeatBidBuilder(Seq(correctBid))
      .withGroup(2)
      .build
    val incorrectBidResponse = BidResponseBuilder("123", Seq(incorrectSeatBid)).build

    validator.validate(incorrectBidResponse) shouldBe false
  }

  it should "not validate BidResponse with negative price" in {
    val incorrectBid = BidBuilder("123", "456", BigDecimal(-1)).build
    val incorrectSeatBid = SeatBidBuilder(Seq(incorrectBid)).build
    val incorrectBidResponse = BidResponseBuilder("123", Seq(incorrectSeatBid)).build

    validator.validate(incorrectBidResponse) shouldBe false
  }
}
