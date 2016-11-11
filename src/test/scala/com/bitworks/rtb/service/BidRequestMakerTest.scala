package com.bitworks.rtb.service

import com.bitworks.rtb.application.RtbModule
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.request.builder.BidRequestBuilder
import com.bitworks.rtb.model.response.builder.BidResponseBuilder
import com.bitworks.rtb.service.parser.BidResponseParser
import com.bitworks.rtb.service.writer.BidRequestWriter
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import scaldi.Module

import scala.concurrent.Future
import scaldi.Injectable._

/**
  * Test for [[com.bitworks.rtb.service.BidRequestMakerImpl BidRequestMakerImpl]].
  *
  * @author Egor Ilchenko
  */
class BidRequestMakerTest extends FlatSpec with OneInstancePerTest
  with MockFactory with ScalaFutures with Matchers {

  // stubs configuration
  val bidRequest = BidRequestBuilder("someId", Seq.empty).build
  val bidResponse = BidResponseBuilder("123", Seq.empty).build
  val bidder = Bidder(123, "name", "endpoint")

  val writerStub = stub[BidRequestWriter]
  (writerStub.write _).when(*).returns(new Array[Byte](0))

  val parserStub = stub[BidResponseParser]
  (parserStub.parse _).when(*).returns(bidResponse)

  val requestMakerStub = stub[RequestMaker]
  (requestMakerStub.post _).when(*, *).returns(Future.successful(new Array[Byte](0)))

  implicit val predefined = new Module {
    bind[BidRequestWriter] to writerStub
    bind[BidResponseParser] to parserStub
    bind[RequestMaker] to requestMakerStub
  } :: new RtbModule


  "BidRequestMaker" should "write request before sending" in {
    val bidRequestMaker = inject[BidRequestMaker]
    bidRequestMaker.send(bidder, bidRequest)

    (writerStub.write _).verify(bidRequest).once
  }

  it should "POST writed request correctly" in {
    val array = new Array[Byte](24)

    val writerStub = stub[BidRequestWriter]
    (writerStub.write _).when(*).returns(array)

    val module = new Module {
      bind[BidRequestWriter] to writerStub
    } :: predefined

    val bidRequestMaker = inject[BidRequestMaker]
    bidRequestMaker.send(bidder, bidRequest)

    (requestMakerStub.post _).verify(bidder.endpoint, array).once
  }

  it should "parse received bytes" in {
    val array = new Array[Byte](24)

    val requestMakerStub = stub[RequestMaker]
    (requestMakerStub.post _).when(*, *).returns(Future.successful(array)).once

    val module = new Module {
      bind[RequestMaker] to requestMakerStub
    } :: predefined

    val bidRequestMaker = inject[BidRequestMaker]
    bidRequestMaker.send(bidder, bidRequest)

    (parserStub.parse _).verify(array).once
  }

  it should "return parsed BidResponse" in {
    val bidRequestMaker = inject[BidRequestMaker]
    val fBidResponse = bidRequestMaker.send(bidder, bidRequest)

    whenReady(fBidResponse) { result =>
      result shouldBe bidResponse
    }
  }

}
