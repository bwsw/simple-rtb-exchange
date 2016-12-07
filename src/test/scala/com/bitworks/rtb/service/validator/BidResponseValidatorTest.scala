package com.bitworks.rtb.service.validator

import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.model.response.builder._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.TableDrivenPropertyChecks._

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

  it should "not validate BidResponse with a different id" in {
    val bidResponse = BidResponseBuilder("some_id", Seq(correctSeatBid)).build
    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with nbr" in {
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq.empty).withNbr(1).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with empty bid id" in {
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(correctSeatBid)).withBidId("").build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with incorrect impId" in {
    val bid = BidBuilder("1", "not_imp_id", 10).build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with empty customData" in {
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(correctSeatBid)).withCustomData("")
      .build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with empty cur" in {
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(correctSeatBid)).withCur("").build

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

  it should "not validate BidResponse with empty seat in SeatBid" in {
    val seatBid = SeatBidBuilder(Seq(correctBid)).withSeat("").build

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

  it should "not validate BidResponse without matching dealId" in {
    val bid = BidBuilder("1", imp.id, imp.bidFloor + 0.1)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .withDealId(deal.id + "some")
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse without invalid price for deal" in {
    val bid = BidBuilder("1", imp.id, deal.bidFloor - 0.1)
      .withAdomain(correctBid.adomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .withDealId(deal.id)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse without invalid advertiser domain for deal" in {
    val bid = BidBuilder("1", imp.id, deal.bidFloor + 0.1)
      .withAdomain(deal.wadomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .withDealId(deal.id)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse without invalid seat" in {
    val bid = BidBuilder("1", imp.id, deal.bidFloor + 0.1)
      .withAdomain(deal.wadomain.get)
      .withCat(correctBid.cat.get)
      .withAttr(correctBid.attr.get)
      .withH(correctBid.h.get)
      .withW(correctBid.w.get)
      .withAdm(correctBid.adm.get)
      .withDealId(deal.id)
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).withSeat("some").build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with attr blocked in banner" in {
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

  val incorrectSizes = Table(
    ("min", "exp", "max", "got"),
    (Some(100), Some(200), Some(300), None),
    (Some(100), None, Some(300), None),
    (Some(100), Some(200), None, None),
    (None, Some(200), Some(300), None),
    (None, None, Some(300), None),
    (Some(100), Some(200), Some(300), Some(0)),
    (None, None, None, Some(0)),
    (Some(100), Some(200), Some(300), Some(99)),
    (Some(100), Some(200), Some(300), Some(301)),
    (None, Some(200), None, Some(201)))

  it should "not validate BidResponse with invalid banner height" in {
    forAll(incorrectSizes) {
      (min: Option[Int], exp: Option[Int], max: Option[Int], got: Option[Int]) =>
        val bannerBuilder = BannerBuilder()
        min.foreach(bannerBuilder.withHmin)
        exp.foreach(bannerBuilder.withH)
        max.foreach(bannerBuilder.withHmax)
        val banner = bannerBuilder.build
        val imp = ImpBuilder("1").withBanner(banner).build
        val bidRequest = BidRequestBuilder("987349863", Seq(imp)).build

        val bidBuilder = BidBuilder("1", imp.id, 1).withAdm("adm")
        got.foreach(bidBuilder.withH)
        val bid = bidBuilder.build
        val seatBid = SeatBidBuilder(Seq(bid)).build
        val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

        validator.validate(bidRequest, bidResponse) shouldBe None
    }
  }

  it should "not validate BidResponse with invalid banner width" in {
    forAll(incorrectSizes) {
      (min: Option[Int], exp: Option[Int], max: Option[Int], got: Option[Int]) =>
        val bannerBuilder = BannerBuilder()
        min.foreach(bannerBuilder.withWmin)
        exp.foreach(bannerBuilder.withW)
        max.foreach(bannerBuilder.withWmax)
        val banner = bannerBuilder.build
        val imp = ImpBuilder("1").withBanner(banner).build
        val bidRequest = BidRequestBuilder("987349863", Seq(imp)).build

        val bidBuilder = BidBuilder("1", imp.id, 1).withAdm("adm")
        got.foreach(bidBuilder.withW)
        val bid = bidBuilder.build
        val seatBid = SeatBidBuilder(Seq(bid)).build
        val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

        validator.validate(bidRequest, bidResponse) shouldBe None
    }
  }

  val correctSizes = Table(
    ("min", "exp", "max", "got"),
    (Some(100), Some(200), Some(300), Some(100)),
    (Some(100), Some(200), Some(300), Some(120)),
    (Some(100), Some(200), Some(300), Some(200)),
    (Some(100), Some(200), Some(300), Some(230)),
    (Some(100), Some(200), Some(300), Some(300)),
    (None, Some(200), Some(300), Some(1)),
    (None, None, Some(300), Some(1)),
    (Some(100), Some(200), None, Some(Integer.MAX_VALUE)),
    (Some(100), None, None, Some(Integer.MAX_VALUE)),
    (None, Some(200), None, Some(200)),
    (None, None, None, Some(200)),
    (None, None, None, None))

  it should "validate BidResponse with banner width" in {
    forAll(correctSizes) {
      (min: Option[Int], exp: Option[Int], max: Option[Int], got: Option[Int]) =>
        val bannerBuilder = BannerBuilder()
        min.foreach(bannerBuilder.withWmin)
        exp.foreach(bannerBuilder.withW)
        max.foreach(bannerBuilder.withWmax)
        val banner = bannerBuilder.build
        val imp = ImpBuilder("1").withBanner(banner).build
        val bidRequest = BidRequestBuilder("987349863", Seq(imp)).build

        val bidBuilder = BidBuilder("1", imp.id, 1).withAdm("adm")
        got.foreach(bidBuilder.withW)
        val bid = bidBuilder.build
        val seatBid = SeatBidBuilder(Seq(bid)).build
        val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

        validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
    }
  }

  it should "validate BidResponse with banner height" in {
    forAll(correctSizes) {
      (min: Option[Int], exp: Option[Int], max: Option[Int], got: Option[Int]) =>
        val bannerBuilder = BannerBuilder()
        min.foreach(bannerBuilder.withHmin)
        exp.foreach(bannerBuilder.withH)
        max.foreach(bannerBuilder.withHmax)
        val banner = bannerBuilder.build
        val imp = ImpBuilder("1").withBanner(banner).build
        val bidRequest = BidRequestBuilder("987349863", Seq(imp)).build

        val bidBuilder = BidBuilder("1", imp.id, 1).withAdm("adm")
        got.foreach(bidBuilder.withH)
        val bid = bidBuilder.build
        val seatBid = SeatBidBuilder(Seq(bid)).build
        val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

        validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
    }
  }

  it should "validate BidResponse for BidRequest with video" in {
    val video = VideoBuilder(Seq("video/x-flv")).build
    val imp = ImpBuilder("4").withVideo(video).build
    val bidRequest = BidRequestBuilder("5783216", Seq(imp)).build

    val bid = BidBuilder("5", imp.id, imp.bidFloor).withAdm("adm").build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
  }

  it should "validate BidResponse for BidRequest with native" in {
    val native = NativeBuilder("native request...").build
    val imp = ImpBuilder("4").withNative(native).build
    val bidRequest = BidRequestBuilder("5783216", Seq(imp)).build

    val bid = BidBuilder("5", imp.id, imp.bidFloor).withAdm("adm").build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
  }

  it should "not validate BidResponse with attr blocked in video" in {
    val video = VideoBuilder(Seq("video/x-flv")).withBattr(Seq(1, 2, 3)).build
    val imp = ImpBuilder("4").withVideo(video).build
    val bidRequest = BidRequestBuilder("5783216", Seq(imp)).build

    val bid = BidBuilder("5", imp.id, imp.bidFloor)
      .withAdm("adm")
      .withAttr(Set(6, 2, 5))
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "not validate BidResponse with attr blocked in native" in {
    val native = NativeBuilder("native request...").withBattr(Seq(1, 2, 3)).build
    val imp = ImpBuilder("4").withNative(native).build
    val bidRequest = BidRequestBuilder("5783216", Seq(imp)).build

    val bid = BidBuilder("5", imp.id, imp.bidFloor)
      .withAdm("adm")
      .withAttr(Set(6, 2, 5))
      .build
    val seatBid = SeatBidBuilder(Seq(bid)).build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe None
  }

  it should "validate BidResponse for empty whitelist in Deal" in {
    val seat = "seat1"
    val deal = DealBuilder("deal1243").withWseat(Seq(seat)).build
    val pmp = PmpBuilder().withDeals(Seq(deal)).build
    val imp = ImpBuilder("13512532").withPmp(pmp).build
    val bidRequest = BidRequestBuilder("19875198", Seq(imp)).build

    val bid = BidBuilder("1", imp.id, 1)
      .withAdm("\"<img src=\\\"http://localhost/img.jpg\\\" />\"")
      .withAdomain(Seq("domain.com"))
      .withDealId(deal.id)
      .build
    val seatBid = SeatBidBuilder(Seq(bid))
      .withSeat(seat)
      .withGroup(1)
      .build
    val bidResponse = BidResponseBuilder(bidRequest.id, Seq(seatBid)).build

    validator.validate(bidRequest, bidResponse) shouldBe Some(bidResponse)
  }

}
