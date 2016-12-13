package com.bitworks.rtb.service.actor

import java.util.concurrent.TimeoutException

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.TestActorRef
import akka.util.Timeout
import com.bitworks.rtb.model.message.{CreateAdResponse, SendWinNotice}
import com.bitworks.rtb.model.request.builder.BidRequestBuilder
import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import com.bitworks.rtb.service.{Configuration, WinNoticeRequestMaker}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import scaldi.Module

import scala.concurrent.duration.{FiniteDuration, _}
import scala.concurrent.{Await, Future}
import org.easymock.EasyMock._

/**
  * Test for [[com.bitworks.rtb.service.actor.WinActor WinActor]].
  *
  * @author Egor Ilchenko
  */
class WinActorTest extends FlatSpec with Matchers with EasyMockSugar with ScalaFutures with
  OneInstancePerTest {

  val configuration = mock[Configuration]
  expecting {
    call(configuration.winNoticeTimeout).andReturn(FiniteDuration(1, "s")).anyTimes()
  }

  implicit val timeout: Timeout = 1.second
  val duration = Duration(1, "s")
  implicit val system = ActorSystem("test")

  "WinActor" should "send win notices to bidders if admarkup is in bid response" in {
    val bidRequest = BidRequestBuilder("reqid", Seq.empty).build
    val bid = BidBuilder("bidid", "impid", BigDecimal(0)).withAdm("someAdm").withNurl("one").build
    val bid1 = BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("three").build
    val bid2 = BidBuilder("bidid", "impid", BigDecimal(0)).withAdm("someAdm").withNurl("two")
      .build
    val seatBid = SeatBidBuilder(Seq(bid, bid1, bid2)).build
    val bidResponse = BidResponseBuilder("respid", Seq(seatBid)).build

    val requestMaker = niceMock[WinNoticeRequestMaker]
    expecting {
      requestMaker.replaceMacros("one", bidRequest, bidResponse, seatBid, bid).andStubReturn("one")
      requestMaker.replaceMacros("three", bidRequest, bidResponse, seatBid, bid1)
        .andStubReturn("three")
      requestMaker.replaceMacros("two", bidRequest, bidResponse, seatBid, bid2)
        .andStubReturn("two")
      requestMaker.sendWinNotice("one").andReturn(Future.failed(new TimeoutException())).times(1)
      requestMaker.sendWinNotice("two").andReturn(Future.failed(new TimeoutException())).times(1)
      requestMaker.getAdMarkup("three").andStubReturn(Future.successful("stringgg"))
    }

    implicit val module = new Module {
      bind[Configuration] to configuration
      bind[WinNoticeRequestMaker] to requestMaker
    }
    implicit val context = system.dispatcher

    whenExecuting(requestMaker, configuration) {
      val actor = TestActorRef(new WinActor)
      val fAnswer = (actor ? SendWinNotice(bidRequest, Seq(bidResponse)))
        .recover { case _ => bidResponse }

      Await.ready(fAnswer, duration)
    }

  }

  it should "get ad markup for bids without admarkup" in {
    val bidRequest = BidRequestBuilder("reqid", Seq.empty).build
    val bid = BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("one").build
    val bid1 = BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("three").withAdm("someAdm")
      .build
    val bid2 = BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("two")
      .build
    val seatBid = SeatBidBuilder(Seq(bid, bid1, bid2)).build
    val bidResponse = BidResponseBuilder("respid", Seq(seatBid)).build

    val requestMaker = niceMock[WinNoticeRequestMaker]
    expecting {
      requestMaker.replaceMacros("one", bidRequest, bidResponse, seatBid, bid).andStubReturn("one")
      requestMaker.replaceMacros("three", bidRequest, bidResponse, seatBid, bid1)
        .andStubReturn("three")
      requestMaker.replaceMacros("two", bidRequest, bidResponse, seatBid, bid2)
        .andStubReturn("two")
      requestMaker.getAdMarkup("one").andReturn(Future.failed(new TimeoutException())).times(1)
      requestMaker.getAdMarkup("two").andReturn(Future.failed(new TimeoutException())).times(1)
    }

    implicit val module = new Module {
      bind[Configuration] to configuration
      bind[WinNoticeRequestMaker] to requestMaker
    }
    implicit val context = system.dispatcher

    whenExecuting(requestMaker, configuration) {
      val actor = TestActorRef(new WinActor)
      val fAnswer = (actor ? SendWinNotice(bidRequest, Seq(bidResponse)))
        .recover { case _ => bidResponse }

      Await.ready(fAnswer, duration)
    }
  }

  it should "insert received ad markup to bids" in {
    val bidRequest = BidRequestBuilder("reqid", Seq.empty).build
    val bid = BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("one").build
    val bid1 = BidBuilder("bidid", "impid", BigDecimal(0))
      .withNurl("three")
      .withAdm("someAdm")
      .build
    val bid2 = BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("two").build
    val bid3 = BidBuilder("bidid", "impid", BigDecimal(0)).withAdm("adm").build
    val seatBid = SeatBidBuilder(Seq(bid, bid1, bid2, bid3)).build
    val bidResponse = BidResponseBuilder("respid", Seq(seatBid)).build

    val admone = "admone"
    val admtwo = "admtwo"

    val requestMaker = strictMock[WinNoticeRequestMaker]
    expecting {
      requestMaker.replaceMacros("one", bidRequest, bidResponse, seatBid, bid).andStubReturn("one")
      requestMaker.getAdMarkup("one").andReturn(Future.successful(admone)).times(1)

      requestMaker.replaceMacros(admone, bidRequest, bidResponse, seatBid, bid)
        .andStubReturn(admone)

      requestMaker.replaceMacros("three", bidRequest, bidResponse, seatBid, bid1)
        .andStubReturn("three")
      requestMaker.sendWinNotice(anyObject()).andStubReturn(Future.failed(new TimeoutException()))
      requestMaker.replaceMacros("someAdm", bidRequest, bidResponse, seatBid, bid1)
        .andStubReturn("someAdm")

      requestMaker.replaceMacros("two", bidRequest, bidResponse, seatBid, bid2).andStubReturn("two")

      requestMaker.getAdMarkup("two").andReturn(Future.successful(admtwo)).times(1)
      requestMaker.replaceMacros(admtwo, bidRequest, bidResponse, seatBid, bid2)
        .andStubReturn(admtwo)

      requestMaker.replaceMacros("adm", bidRequest, bidResponse, seatBid, bid3).andStubReturn("adm")
    }

    implicit val module = new Module {
      bind[Configuration] to configuration
      bind[WinNoticeRequestMaker] to requestMaker
    }
    implicit val context = system.dispatcher

    val expectedBidResponse = BidResponseBuilder(
      "respid",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("bidid", "impid", BigDecimal(0)).withAdm(admone).withNurl("one").build,
            BidBuilder("bidid", "impid", BigDecimal(0)).withAdm("someAdm").withNurl("three").build,
            BidBuilder("bidid", "impid", BigDecimal(0)).withAdm(admtwo).withNurl("two").build,
            bid3)
        ).build))
      .build

    whenExecuting(requestMaker, configuration) {
      val actor = TestActorRef(new WinActor)
      val fAnswer = actor ? SendWinNotice(bidRequest, Seq(bidResponse))

      val ans = Await.result(fAnswer, duration)
      ans shouldBe CreateAdResponse(Seq(expectedBidResponse))
    }
  }

}
