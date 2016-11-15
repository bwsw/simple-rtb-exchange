package com.bitworks.rtb.service.validator

import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.model.response.builder._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.service.validator.BidResponseValidator BidResponseValidator]].
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
    .withBattr(Seq(1, 2))
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
    .withAdm("\"<img src=\\\"http://localhost/img.jpg\\\" />\"")
    .withAdomain(deal.wadomain.get)
    .withBundle(app.bundle.get)
    .withCat(Seq("IAB2-2"))
    .withAttr(Set(5, 6, 7))
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
    val bidResponse = BidResponseBuilder("fh2i8", Seq(correctSeatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse without seatBids and nbr" in {
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq.empty).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse without Bids in SeatBid" in {
    val seatBid = SeatBidBuilder(Seq.empty).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with incorrect group in SeatBid" in {
    val seatBid = SeatBidBuilder(Seq(correctBid))
      .withGroup(2)
      .build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with invalid price" in {
    val bid = BidBuilder("1", imp.id, 0.0)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with small price" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor - 0.01)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "remove incorrect Bids from SeatBid when SeatBid.group = 0" in {
    val bid = BidBuilder("2", imp.id, -5)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid, correctBid))
      .withGroup(0)
      .withSeat(seat)
      .build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    val expectedSeatBid = SeatBidBuilder(Seq(correctBid)).withSeat(seat).withGroup(0).build
    val expectedBidResponse = BidResponseBuilder(bidRequest.id, Seq(expectedSeatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(expectedBidResponse)
  }

  it should "not validate incorrect Bids when SeatBid.group = 1" in {
    val bid = BidBuilder("2", imp.id, imp.bidFloor - 0.01)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid, correctBid)).withGroup(1).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with blocked categories" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor + 1)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get ++ bidRequest.bcat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with blocked domains" in {
    val incorrectBid = BidBuilder("1", imp.id, imp.bidFloor + 1)
      .withAdomain(correctBid.adomain.get ++ bidRequest.badv.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(incorrectBid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "validate BidResponse without dealId" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
  }

  it should "not validate BidResponse with blocked attr" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get ++ banner.battr.get.toSet)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with incorrect size" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(banner.hmax.get + 1)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with invalid height" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(0)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with invalid width" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(0)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "validate BidResponse with exact width" in {
    val banner = BannerBuilder().withW(150).build
    val imp = ImpBuilder("13512532").withBanner(banner).build
    val bidRequest = BidRequestBuilder("19875198", Seq(imp)).build

    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withW(banner.w.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
  }

  it should "validate BidResponse with exact height" in {
    val banner = BannerBuilder().withH(150).build
    val imp = ImpBuilder("13512532").withBanner(banner).build
    val bidRequest = BidRequestBuilder("19875198", Seq(imp)).build

    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withH(banner.h.get)
      .withAdm(correctBid.adm.get)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
  }

}
