package com.bitworks.rtb.service

import akka.actor.ActorSystem
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.http._
import com.bitworks.rtb.model.request.builder.BidRequestBuilder
import com.bitworks.rtb.model.response.builder.BidResponseBuilder
import com.bitworks.rtb.service.factory.BidModelsConverter
import org.easymock.EasyMock._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers, OneInstancePerTest}
import scaldi.Injectable._
import scaldi.Module

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Test for [[com.bitworks.rtb.service.BidRequestMakerImpl BidRequestMakerImpl]].
  *
  * @author Egor Ilchenko
  */
class BidRequestMakerTest extends FlatSpec with BeforeAndAfterEach
  with EasyMockSugar with ScalaFutures with Matchers with OneInstancePerTest {

  val bidRequest = BidRequestBuilder("someId", Seq.empty).build
  val bidResponse = BidResponseBuilder("123", Seq.empty).build
  val bidder = Bidder(123, "name", "endpoint")
  val ct = Json

  val bidConverter = mock[BidModelsConverter]
  bidConverter.write(anyObject(), same(ct)).andStubReturn(new Array[Byte](0))
  bidConverter.parse(anyObject(), same(ct)).andStubReturn(bidResponse)

  val requestMakerMock = mock[HttpRequestMaker]
  requestMakerMock.make(anyObject()).andStubReturn(
    Future.successful(HttpResponseModel(new Array[Byte](0), 200, ct, Seq.empty)))

  val configurationMock = mock[Configuration]
  configurationMock.bidRequestContentType.andStubReturn(ct)

  implicit val predefined = new Module {
    bind[BidModelsConverter] to bidConverter
    bind[HttpRequestMaker] to requestMakerMock
    bind[Configuration] to configurationMock
    bind[ActorSystem] to ActorSystem()
    bind[BidRequestMaker] to injected[BidRequestMakerImpl]
  }

  override def beforeEach = {
    try {
      replay(
        bidConverter,
        requestMakerMock,
        configurationMock)
    } catch {
      case e: Throwable =>
    }
  }

  override def afterEach = {
    verify(
      bidConverter,
      requestMakerMock,
      configurationMock)
  }

  "BidRequestMaker" should "write request before sending" in {
    val converterMock = mock[BidModelsConverter]
    expecting {
      converterMock.write(bidRequest, ct).andReturn(new Array[Byte](0)).times(1)
    }

    val module = new Module {
      bind[BidModelsConverter] toNonLazy converterMock
    } :: predefined

    whenExecuting(converterMock) {
      val bidRequestMaker = inject[BidRequestMaker]
      bidRequestMaker.send(bidder, bidRequest)
    }
  }

  it should "POST writed request correctly" in {
    val array = new Array[Byte](24)

    val converterMock = mock[BidModelsConverter]
    val requestMakerMock = mock[HttpRequestMaker]
    expecting {
      converterMock.write(bidRequest, ct).andReturn(array).times(1)
      requestMakerMock
        .make(HttpRequestModel(bidder.endpoint, POST, Some(array)))
        .andReturn(Future.failed(new RuntimeException))
        .times(1)
    }

    val module = new Module {
      bind[BidModelsConverter] to converterMock
      bind[HttpRequestMaker] to requestMakerMock
    } :: predefined

    whenExecuting(converterMock, requestMakerMock) {
      val bidRequestMaker = inject[BidRequestMaker]
      bidRequestMaker.send(bidder, bidRequest)
    }
  }

  it should "parse received bytes" in {
    val array = new Array[Byte](24)

    val converterMock = mock[BidModelsConverter]
    val requestMakerMock = mock[HttpRequestMaker]
    expecting {
      converterMock.write(anyObject(), same(ct)).andStubReturn(array)
      converterMock.parse(array, ct).andReturn(bidResponse).times(1)
      requestMakerMock
        .make(anyObject())
        .andReturn(
          Future.successful(
            HttpResponseModel(array, 200, ct, Seq.empty)))
        .times(1)
    }

    val module = new Module {
      bind[HttpRequestMaker] to requestMakerMock
      bind[BidModelsConverter] to converterMock
    } :: predefined

    whenExecuting(requestMakerMock, converterMock) {
      val bidRequestMaker = inject[BidRequestMaker]
      Await.ready(bidRequestMaker.send(bidder, bidRequest), 1.second)
    }
  }

  it should "return parsed BidResponse" in {
    val bidRequestMaker = inject[BidRequestMaker]
    val fBidResponse = bidRequestMaker.send(bidder, bidRequest)

    whenReady(fBidResponse) { result =>
      result shouldBe bidResponse
    }
  }
}
