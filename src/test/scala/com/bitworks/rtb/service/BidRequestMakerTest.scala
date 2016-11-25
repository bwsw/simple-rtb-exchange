package com.bitworks.rtb.service

import akka.actor.ActorSystem
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.http._
import com.bitworks.rtb.model.request.builder.BidRequestBuilder
import com.bitworks.rtb.model.response.builder.BidResponseBuilder
import com.bitworks.rtb.service.parser.{BidResponseParser, BidResponseParserFactory}
import com.bitworks.rtb.service.writer.{BidRequestWriter, BidRequestWriterFactory}
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
  with EasyMockSugar with ScalaFutures with Matchers with OneInstancePerTest{

  val bidRequest = BidRequestBuilder("someId", Seq.empty).build
  val bidResponse = BidResponseBuilder("123", Seq.empty).build
  val bidder = Bidder(123, "name", "endpoint")

  val writerMock = mock[BidRequestWriter]
  writerMock.write(anyObject()).andStubReturn(new Array[Byte](0))

  val parserMock = mock[BidResponseParser]
  parserMock.parse(anyObject()).andStubReturn(bidResponse)

  val writerFactoryMock = mock[BidRequestWriterFactory]
  writerFactoryMock.getWriter(anyObject()).andStubReturn(writerMock)

  val parserFactoryMock = mock[BidResponseParserFactory]
  parserFactoryMock.getParser(anyObject()).andStubReturn(parserMock)

  val requestMakerMock = mock[HttpRequestMaker]
  requestMakerMock.make(anyObject()).andStubReturn(
    Future.successful(HttpResponseModel(new Array[Byte](0), 200, Unknown)))

  val configurationMock = mock[Configuration]
  configurationMock.bidRequestContentType.andStubReturn(Json)

  implicit val predefined = new Module {
    bind[BidRequestWriterFactory] to writerFactoryMock
    bind[BidResponseParserFactory] to parserFactoryMock
    bind[HttpRequestMaker] to requestMakerMock
    bind[Configuration] to configurationMock
    bind[ActorSystem] to ActorSystem()
    bind[BidRequestMaker] to injected[BidRequestMakerImpl]
  }

  override def beforeEach = {
    try{
      replay(
        writerMock,
        parserMock,
        writerFactoryMock,
        parserFactoryMock,
        requestMakerMock,
        configurationMock)
    } catch{
      case e: Throwable =>
    }
  }

  override def afterEach = {
    verify(
      writerMock,
      parserMock,
      writerFactoryMock,
      parserFactoryMock,
      requestMakerMock,
      configurationMock)
  }

  "BidRequestMaker" should "write request before sending" in {
    val writerMock = mock[BidRequestWriter]
    writerMock.write(anyObject()).andReturn(new Array[Byte](0)).times(1)

    val writerFactoryMock = mock[BidRequestWriterFactory]
    writerFactoryMock.getWriter(anyObject()).andReturn(writerMock).times(1)

    val module = new Module {
      bind[BidRequestWriterFactory] toNonLazy writerFactoryMock
    } :: predefined

    whenExecuting(writerMock, writerFactoryMock) {
      val bidRequestMaker = inject[BidRequestMaker]
      bidRequestMaker.send(bidder, bidRequest)
    }
  }

  it should "POST writed request correctly" in {
    val array = new Array[Byte](24)

    val writerMock = mock[BidRequestWriter]
    writerMock.write(anyObject()).andReturn(array).times(1)

    val writerFactoryMock = mock[BidRequestWriterFactory]
    writerFactoryMock.getWriter(anyObject()).andReturn(writerMock).times(1)

    val requestMakerMock = mock[HttpRequestMaker]
    requestMakerMock
      .make(HttpRequestModel(bidder.endpoint, POST, Some(array)))
      .andReturn(Future.failed(new RuntimeException))
      .times(1)

    val module = new Module {
      bind[BidRequestWriterFactory] to writerFactoryMock
      bind[HttpRequestMaker] to requestMakerMock
    } :: predefined

    whenExecuting(writerMock, writerFactoryMock, requestMakerMock) {
      val bidRequestMaker = inject[BidRequestMaker]
      bidRequestMaker.send(bidder, bidRequest)
    }
  }

  it should "parse received bytes" in {
    val array = new Array[Byte](24)

    val requestMakerMock = mock[HttpRequestMaker]
    requestMakerMock
      .make(anyObject())
      .andReturn(
        Future.successful(
          HttpResponseModel(array, 200, Unknown)))
      .times(1)

    val parserMock = mock[BidResponseParser]
    parserMock.parse(anyObject()).andReturn(bidResponse).times(1)

    val parserFactoryMock = mock[BidResponseParserFactory]
    parserFactoryMock.getParser(anyObject()).andReturn(parserMock).times(1)

    val module = new Module {
      bind[HttpRequestMaker] to requestMakerMock
      bind[BidResponseParserFactory] to parserFactoryMock
    } :: predefined

    whenExecuting(requestMakerMock, parserMock, parserFactoryMock) {
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
