package com.bitworks.rtb.service

import akka.actor.ActorSystem
import com.bitworks.rtb.model.http.{GET, HttpRequestModel, HttpResponseBody, HttpResponseModel}
import com.bitworks.rtb.model.request.builder.BidRequestBuilder
import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Future


/**
  * Test for [[com.bitworks.rtb.service.WinNoticeRequestMakerImpl WinNoticeRequestMakerImpl]].
  *
  * @author Egor Ilchenko
  */
class WinNoticeRequestMakerTest extends FlatSpec with Matchers with EasyMockSugar with
  ScalaFutures {


  val system = ActorSystem("test")


  "WinNoticeRequestMaker" should "send win notice correctly" in {
    val expectingRequest = HttpRequestModel("someuri", GET)
    val response = HttpResponseModel(HttpResponseBody(new Array[Byte](0), ""), 200)
    val httpRequestMakerMock = mock[HttpRequestMaker]
    expecting {
      httpRequestMakerMock.make(expectingRequest).andReturn(Future.successful(response)).times(1)
    }

    val winNoticeRequestMaker = new WinNoticeRequestMakerImpl(httpRequestMakerMock, system)
    whenExecuting(httpRequestMakerMock) {
      winNoticeRequestMaker.sendWinNotice(expectingRequest.uri)
    }
  }

  it should "get ad markup correctly" in {
    val expectingRequest = HttpRequestModel("someuri", GET)
    val response = HttpResponseModel(HttpResponseBody(new Array[Byte](0), "body"), 200)
    val httpRequestMakerMock = mock[HttpRequestMaker]
    expecting {
      httpRequestMakerMock.make(expectingRequest).andReturn(Future.successful(response)).times(1)
    }

    val winNoticeRequestMaker = new WinNoticeRequestMakerImpl(httpRequestMakerMock, system)

    whenExecuting(httpRequestMakerMock) {
      val fBody = winNoticeRequestMaker.getAdMarkup(expectingRequest.uri)
      whenReady(fBody) { str =>
        str shouldBe response.body.string
      }
    }

  }

  it should "replace strings correctly" in {
    val httpRequestMakerMock = mock[HttpRequestMaker]
    val winNoticeRequestMaker = new WinNoticeRequestMakerImpl(httpRequestMakerMock, system)

    val source = "123456"
    val replacements = Seq("12" -> "ab", "45" -> "cd")

    val result = winNoticeRequestMaker.replace(source, replacements)

    val expected = "ab3cd6"

    result shouldBe expected
  }

  it should "replace macros in win url correctly" in {
    val httpRequestMakerMock = mock[HttpRequestMaker]
    val winNoticeRequestMaker = new WinNoticeRequestMakerImpl(httpRequestMakerMock, system)

    val request = BidRequestBuilder("reqid", Seq.empty).build
    val response = BidResponseBuilder("respid", Seq.empty).withCur("EUR").build
    val seatBid = SeatBidBuilder(Seq.empty).withSeat("seat").build
    val bid = BidBuilder("bidid", "impid", BigDecimal("42")).withAdId("adid").build

    val sourceWinUrl = "http://smth.smth/${AUCTION_ID}/${AUCTION_BID_ID}/${AUCTION_IMP_ID}" +
      "/${AUCTION_SEAT_ID}/${AUCTION_AD_ID}/${AUCTION_PRICE}/${AUCTION_CURRENCY}"

    val result = winNoticeRequestMaker.substituteNurl(sourceWinUrl, request, response, seatBid, bid)

    val expected = s"http://smth.smth/${request.id}/${bid.id}/${bid.impId}" +
      s"/${seatBid.seat.get}/${bid.adId.get}/${bid.price.toString}/${response.cur}"

    result shouldBe expected
  }

  it should "replace all macros in all bids correctly" in {
    val httpRequestMakerMock = mock[HttpRequestMaker]
    val winNoticeRequestMaker = new WinNoticeRequestMakerImpl(httpRequestMakerMock, system)

    val response = BidResponseBuilder(
      "respid",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("bidid", "impid", BigDecimal("42"))
              .withNurl("${AUCTION_ID}/${AUCTION_BID_ID}/${AUCTION_SEAT_ID}/${AUCTION_CURRENCY}")
              .build,
            BidBuilder("empty", "empty", BigDecimal("0"))
              .build)
        )
          .withSeat("seat")
          .build))
      .build
    val request = BidRequestBuilder("reqid", Seq.empty).build

    val result = winNoticeRequestMaker.prepareResponses(Seq(response), request)
    val bid = result.flatMap(_.seatBid).head.bid.head
    val expectedBidNurl = s"${request.id}/${response.seatBid.head.bid.head.id}" +
      s"/${response.seatBid.head.seat.get}/${response.cur}"

    bid.nurl.get shouldBe expectedBidNurl

    val emptyBid = result.flatMap(_.seatBid).head.bid.last

    emptyBid.nurl should not be defined
  }

}
