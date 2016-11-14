package com.bitworks.rtb.service.validator

import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.model.response.builder._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.service.validator.BidResponseValidator BidResponseValidator]]
  *
  * @author Pavel Tomskikh
  */
class BidResponseValidatorTest extends FlatSpec with Matchers {

  val seat = "seat1"
  val deal = DealBuilder("deal1243")
    .withBidFloor(1.2)
    .withWadomain(Seq("good.com"))
    .withWseat(Seq(seat))
    .build
  val pmp = PmpBuilder().withDeals(Seq(deal)).build
  val app = AppBuilder().withBundle("app.com").build
  val banner = BannerBuilder()
    .withHmax(400)
    .withHmin(200)
    .withW(150)
    .build
  val imp = ImpBuilder("13512532")
    .withBidFloor(deal.bidFloor)
    .withBanner(banner)
    .withPmp(pmp)
    .build
  val bidRequest = BidRequestBuilder("19875198", Seq(imp))
    .withBcat(Seq("IAB7-3"))
    .withBadv(Seq("bad.com"))
    .withApp(app)
    .build

  val validator = new BidResponseValidator

  val correctBid = BidBuilder(
    "1",
    imp.id,
    deal.bidFloor + 0.1)
    .withAdId("adid")
    .withNurl("nurl")
    .withAdm("adm")
    .withAdomain(deal.wadomain.get)
    .withBundle(app.bundle.get)
    .withCat(Seq("IAB2-2"))
    .withAttr(Set(1, 3, 5))
    .withH(banner.hmin.get + 10)
    .withW(banner.w.get)
    .withDealId(deal.id)
    .build
  val correctSeatBid = SeatBidBuilder(Seq(correctBid))
    .withSeat(seat)
    .withGroup(1)
    .build

  "BidResponseValidator" should "validate correct BidResponse" in {
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(correctSeatBid)).build
    validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
  }

  it should "not validate BidResponse with nbr" in {
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq.empty).withNbr(1).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with empty id" in {
    val incorrectBidResponse = BidResponseBuilder("fh2i8", Seq(correctSeatBid)).build

    validator.validate(bidRequest, incorrectBidResponse) shouldBe None
  }

  it should "not validate BidResponse without seatBids and nbr" in {
    val incorrectBidResponse = BidResponseBuilder(bidRequest.id, Seq.empty).build

    validator.validate(bidRequest, incorrectBidResponse) shouldBe None
  }

  it should "not validate BidResponse without Bids in SeatBid" in {
    val incorrectSeatBid = SeatBidBuilder(Seq.empty).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(incorrectSeatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with incorrect group in SeatBid" in {
    val incorrectSeatBid = SeatBidBuilder(Seq(correctBid))
      .withGroup(2)
      .build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(incorrectSeatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with small price" in {
    val incorrectBid = BidBuilder("1", imp.id, imp.bidFloor - 0.01).build
    val seatBid = SeatBidBuilder(Seq(incorrectBid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "remove incorrect Bids from SeatBid when SeatBid.group = 0" in {
    val incorrectBid = BidBuilder("2", imp.id, -5).build
    val seatBid = SeatBidBuilder(Seq(incorrectBid, correctBid))
      .withGroup(0)
      .withSeat(seat)
      .build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    val expectedSeatBid = SeatBidBuilder(Seq(correctBid)).withSeat(seat).withGroup(0).build
    val expectedBidResponse = BidResponseBuilder(bidRequest.id, Seq(expectedSeatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(expectedBidResponse)
  }

  it should "not validate incorrect Bids when SeatBid.group = 1" in {
    val incorrectBid = BidBuilder("2", imp.id, imp.bidFloor - 0.01).build
    val seatBid = SeatBidBuilder(Seq(incorrectBid, correctBid)).withGroup(1).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with blocked categories" in {
    val incorrectBid = BidBuilder("1", imp.id, imp.bidFloor + 1)
      .withCat(bidRequest.bcat.get :+ "IAB4-4")
      .build
    val seatBid = SeatBidBuilder(Seq(incorrectBid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with blocked domains" in {
    val incorrectBid = BidBuilder("1", imp.id, imp.bidFloor + 1)
      .withAdomain(bidRequest.badv.get :+ "notblocked.com")
      .withH(banner.hmax.get)
      .withW(banner.w.get)
      .build
    val seatBid = SeatBidBuilder(Seq(incorrectBid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "validate BidResponse without dealId" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withBundle(app.bundle.get)
      .withH(banner.hmax.get)
      .withW(banner.w.get)
      .withAdm("<img src=\"http://localhost/img.jpg\" />")
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
  }

  it should "not validate BidResponse without bundle" in {
    val incorrectBid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withH(banner.hmax.get)
      .withW(banner.w.get)
      .build
    val seatBid = SeatBidBuilder(Seq(incorrectBid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }
}
