package com.bitworks.rtb.service.actor

import java.util.concurrent.TimeoutException

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.TestActorRef
import akka.util.Timeout
import com.bitworks.rtb.model.message.SendWinNotice
import com.bitworks.rtb.model.request.builder.BidRequestBuilder
import com.bitworks.rtb.model.response.builder.{BidBuilder, BidResponseBuilder, SeatBidBuilder}
import com.bitworks.rtb.service.{Configuration, WinNoticeRequestMaker}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import scaldi.Module

import scala.concurrent.Future
import scala.concurrent.duration.{FiniteDuration, _}

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
  implicit val system = ActorSystem("test")

  "WinActor" should "prepare bid response for sending" in {
    val bidRequest = BidRequestBuilder("reqid", Seq.empty).build
    val bidResponse = BidResponseBuilder("respid", Seq.empty).build

    val requestMaker = niceMock[WinNoticeRequestMaker]
    expecting {
      requestMaker.prepareResponses(Seq(bidResponse), bidRequest).andReturn(Seq(bidResponse)).times(1)
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
      try {
        whenReady(fAnswer) { _ => }
      } catch {
        case _: Throwable =>
      }
    }

  }

  it should "send win notices to bidders if admarkup is in bid response" in {
    val bidRequest = BidRequestBuilder("reqid", Seq.empty).build
    val bidResponse = BidResponseBuilder(
      "respid",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("bidid", "impid", BigDecimal(0)).withAdm("someAdm").withNurl("one").build,
            BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("three").build,
            BidBuilder("bidid", "impid", BigDecimal(0)).withAdm("someAdm").withNurl("two").build)
        ).build))
      .build

    val requestMaker = niceMock[WinNoticeRequestMaker]
    expecting {
      requestMaker.prepareResponses(Seq(bidResponse), bidRequest).andStubReturn(Seq(bidResponse))
      requestMaker.sendWinNotice("one").andReturn(Future.failed(new TimeoutException())).times(1)
      requestMaker.sendWinNotice("two").andReturn(Future.failed(new TimeoutException())).times(1)
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
      try {
        whenReady(fAnswer) { _ => }
      } catch {
        case _: Throwable =>
      }
    }

  }

  it should "get ad markup fir bids without admarkup" in {
    val bidRequest = BidRequestBuilder("reqid", Seq.empty).build
    val bidResponse = BidResponseBuilder(
      "respid",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("one").build,
            BidBuilder("bidid", "impid", BigDecimal(0)).withAdm("someAdm").withNurl("three").build,
            BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("two").build)
        ).build))
      .build

    val requestMaker = niceMock[WinNoticeRequestMaker]
    expecting {
      requestMaker.prepareResponses(Seq(bidResponse), bidRequest).andStubReturn(Seq(bidResponse))
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
      try {
        whenReady(fAnswer) { _ => }
      } catch {
        case _: Throwable =>
      }
    }
  }

  it should "insert received ad markup to bids" in {
    val bidRequest = BidRequestBuilder("reqid", Seq.empty).build
    val bidResponse = BidResponseBuilder(
      "respid",
      Seq(
        SeatBidBuilder(
          Seq(
            BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("one").build,
            BidBuilder("bidid", "impid", BigDecimal(0)).withAdm("someAdm").withNurl("three").build,
            BidBuilder("bidid", "impid", BigDecimal(0)).withNurl("two").build)
        ).build))
      .build

    val admone = "admone"
    val admtwo = "admtwo"

    val requestMaker = niceMock[WinNoticeRequestMaker]
    expecting {
      requestMaker.prepareResponses(Seq(bidResponse), bidRequest).andStubReturn(Seq(bidResponse))
      requestMaker.getAdMarkup("one").andReturn(Future.successful(admone)).times(1)
      requestMaker.getAdMarkup("two").andReturn(Future.successful(admtwo)).times(1)
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
            BidBuilder("bidid", "impid", BigDecimal(0)).withAdm(admtwo).withNurl("two").build)
        ).build))
      .build

    whenExecuting(requestMaker, configuration) {
      val actor = TestActorRef(new WinActor)
      val fAnswer = (actor ? SendWinNotice(bidRequest, Seq(bidResponse)))
        .recover { case _ => bidResponse }
      try {
        whenReady(fAnswer) { ans =>
          ans shouldBe expectedBidResponse
        }
      } catch {
        case _: Throwable =>
      }
    }
  }

}
