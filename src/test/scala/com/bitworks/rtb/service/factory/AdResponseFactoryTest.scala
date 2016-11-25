package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.response.{Error, ErrorCode}
import com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder
import com.bitworks.rtb.model.request.builder.{BannerBuilder, NativeBuilder, VideoBuilder}
import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import com.bitworks.rtb.service.DataValidationException
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for
  * [[com.bitworks.rtb.service.factory.AdResponseFactoryImpl AdResponseFactoryImpl]].
  *
  * @author Egor Ilchenko
  */
class AdResponseFactoryTest extends FlatSpec with Matchers {

  "AdResponseFactory" should "return AdResponse with banner correctly" in {
    val factory = new AdResponseFactoryImpl
    val requestId = "reqId"
    val impId = "impId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      requestId,
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withBanner(BannerBuilder().build)
          .build))
      .build
    val bidResponse = BidResponseBuilder(
      "bidResponseId",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder(
              "bidId",
              impId,
              BigDecimal("0"))
              .withAdm(adMarkup)
              .build))
          .build))
      .build
    val expectedResponse = AdResponseBuilder(requestId)
      .withImp(
        Seq(
          com.bitworks.rtb.model.ad.response.builder.ImpBuilder(
            impId,
            adMarkup,
            1)
            .build
        ))
      .build

    val response = factory.create(adRequest, Seq(bidResponse))

    response shouldBe expectedResponse
  }

  it should "return AdResponse with video correctly" in {
    val factory = new AdResponseFactoryImpl
    val requestId = "reqId"
    val impId = "impId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      requestId,
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withVideo(VideoBuilder(Seq.empty).build)
          .build))
      .build
    val bidResponse = BidResponseBuilder(
      "bidResponseId",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder(
              "bidId",
              impId,
              BigDecimal("0"))
              .withAdm(adMarkup)
              .build))
          .build))
      .build
    val expectedResponse = AdResponseBuilder(requestId)
      .withImp(
        Seq(
          com.bitworks.rtb.model.ad.response.builder.ImpBuilder(
            impId,
            adMarkup,
            2)
            .build
        ))
      .build

    val response = factory.create(adRequest, Seq(bidResponse))

    response shouldBe expectedResponse
  }

  it should "return AdResponse with native correctly" in {
    val factory = new AdResponseFactoryImpl
    val requestId = "reqId"
    val impId = "impId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      requestId,
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withNative(NativeBuilder("nativeRequest").build)
          .build))
      .build
    val bidResponse = BidResponseBuilder(
      "bidResponseId",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder(
              "bidId",
              impId,
              BigDecimal("0"))
              .withAdm(adMarkup)
              .build))
          .build))
      .build
    val expectedResponse = AdResponseBuilder(requestId)
      .withImp(
        Seq(
          com.bitworks.rtb.model.ad.response.builder.ImpBuilder(
            impId,
            adMarkup,
            3)
            .build
        ))
      .build

    val response = factory.create(adRequest, Seq(bidResponse))

    response shouldBe expectedResponse
  }

  it should "throw exception if cant determine type of response imp by request" in {
    val factory = new AdResponseFactoryImpl
    val requestId = "reqId"
    val impId = "impId"
    val anotherImpId = "anotherImpId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      requestId,
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withNative(NativeBuilder("nativeRequest").build)
          .build))
      .build
    val bidResponse = BidResponseBuilder(
      "bidResponseId",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder(
              "bidId",
              anotherImpId,
              BigDecimal("0"))
              .withAdm(adMarkup)
              .build))
          .build))
      .build

    an[DataValidationException] shouldBe thrownBy {
      factory.create(adRequest, Seq(bidResponse))
    }
  }

  it should "throw exception if bid response contains empty adm" in {
    val factory = new AdResponseFactoryImpl
    val requestId = "reqId"
    val impId = "impId"
    val adRequest = AdRequestBuilder(
      requestId,
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withNative(NativeBuilder("nativeRequest").build)
          .build))
      .build
    val bidResponse = BidResponseBuilder(
      "bidResponseId",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder(
              "bidId",
              impId,
              BigDecimal("0"))
              .build))
          .build))
      .build

    an[DataValidationException] shouldBe thrownBy {
      factory.create(adRequest, Seq(bidResponse))
    }
  }

  it should "build ad response with error correctly" in {
    val factory = new AdResponseFactoryImpl
    val error = Error(ErrorCode.NOT_SPECIFIED_ERROR, "123")
    val requestId = "reqId"
    val adRequest = AdRequestBuilder(requestId, Seq()).build
    val expectedResponse = AdResponseBuilder(requestId)
      .withError(error)
      .build

    val response = factory.create(adRequest, error)

    response shouldBe expectedResponse
  }

}
