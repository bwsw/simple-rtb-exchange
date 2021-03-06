package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder
import com.bitworks.rtb.model.ad.response.{Error, ErrorCode}
import com.bitworks.rtb.model.http.{Avro, Json, Protobuf}
import com.bitworks.rtb.model.request.builder.{BannerBuilder, NativeBuilder, VideoBuilder}
import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import com.bitworks.rtb.service.{Configuration, DataValidationException}
import com.typesafe.config.ConfigFactory
import org.easymock.EasyMock._
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConversions.mapAsJavaMap

/**
  * Test for
  * [[com.bitworks.rtb.service.factory.AdResponseFactoryImpl AdResponseFactoryImpl]].
  *
  * @author Egor Ilchenko
  */
class AdResponseFactoryTest extends FlatSpec with Matchers with EasyMockSugar {

  val unknownErrorMsg = "UNKNOWN_ERROR"
  val errorMessages = ConfigFactory.parseMap(Map("UNKNOWN_ERROR" -> unknownErrorMsg))

  val configuration = mock[Configuration]
  expecting {
    configuration.errorMessages.andStubReturn(errorMessages)
    replay(configuration)
  }

  "AdResponseFactory" should "return AdResponse with banner correctly" in {
    val factory = new AdResponseFactoryImpl(configuration)
    val impId = "impId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      "reqId",
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withBanner(BannerBuilder().build)
          .build),
      Json)
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
    val expectedResponse = AdResponseBuilder(adRequest.ct)
      .withId(adRequest.id)
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
    val factory = new AdResponseFactoryImpl(configuration)
    val impId = "impId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      "reqId",
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withVideo(VideoBuilder(Seq.empty).build)
          .build),
      Json)
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
    val expectedResponse = AdResponseBuilder(adRequest.ct)
      .withId(adRequest.id)
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
    val factory = new AdResponseFactoryImpl(configuration)
    val impId = "impId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      "reqId",
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withNative(NativeBuilder("nativeRequest").build)
          .build),
      Json)
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
    val expectedResponse = AdResponseBuilder(adRequest.ct)
      .withId(adRequest.id)
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

  it should "throw exception if cant determine type of all impressions by request" in {
    val factory = new AdResponseFactoryImpl(configuration)
    val impId = "impId"
    val anotherImpId = "anotherImpId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      "reqId",
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withNative(NativeBuilder("nativeRequest").build)
          .build),
      Json)
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

  it should "throw exception if all impressions contains empty adm" in {
    val factory = new AdResponseFactoryImpl(configuration)
    val impId = "impId"
    val adRequest = AdRequestBuilder(
      "reqId",
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withNative(NativeBuilder("nativeRequest").build)
          .build),
      Json)
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
    val factory = new AdResponseFactoryImpl(configuration)
    val error = Error(ErrorCode.UNKNOWN_ERROR.id, unknownErrorMsg)
    val adRequest = AdRequestBuilder("reqId", Seq(), Json).build
    val expectedResponse = AdResponseBuilder(adRequest.ct)
      .withId(adRequest.id)
      .withError(error)
      .build

    val response = factory.create(adRequest, ErrorCode.apply(error.code))

    response shouldBe expectedResponse
  }

  it should "build ad response with error and without ad request correctly" in {
    val factory = new AdResponseFactoryImpl(configuration)
    val error = Error(ErrorCode.UNKNOWN_ERROR.id, unknownErrorMsg)
    Seq(Protobuf, Json, Avro).foreach { ct =>
      val expectedResponse = AdResponseBuilder(ct)
        .withError(error)
        .build

      val response = factory.create(ErrorCode.apply(error.code), ct)

      response shouldBe expectedResponse
    }
  }

  it should "build ad response with valid imps ignoring invalid" in {
    val factory = new AdResponseFactoryImpl(configuration)
    val impId = "impId"
    val adMarkup = "admarkup"
    val adRequest = AdRequestBuilder(
      "reqId",
      Seq(
        com.bitworks.rtb.model.ad.request.builder.ImpBuilder(impId)
          .withBanner(BannerBuilder().build)
          .build),
      Json)
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
              .build,
            BidBuilder(
              "bidId",
              impId,
              BigDecimal("0"))
              .withAdm("")
              .build,
            BidBuilder(
              "bidId",
              impId,
              BigDecimal("0"))
              .build))
          .build))
      .build
    val expectedResponse = AdResponseBuilder(adRequest.ct)
      .withId(adRequest.id)
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
}
