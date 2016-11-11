package com.bitworks.rtb.service.writer

import com.bitworks.rtb.model.request.builder.{BidRequestBuilder, ImpBuilder}
import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

/**
  * Test for [[com.bitworks.rtb.service.writer.BidRequestJsonWriter BidRequestJsonWriter]]
  *
  * @author Pavel Tomskikh
  */
class BidRequestJsonWriterTest extends FlatSpec with Matchers {

  val mapper = new ObjectMapper
  val writer = new BidRequestJsonWriter

  "BidRequestJsonWriter" should "write JSON correctly" in {
    val path = getClass.getResource("bidrequest_with_option.json").getPath
    val expectedJson = (Source fromFile path).mkString
    val expectedJsonNode = mapper.readTree(expectedJson)
    val actualJson = writer.write(BidRequestWithOption.bidRequest)
    val actualJsonNode = mapper.readTree(actualJson)

    actualJsonNode shouldBe expectedJsonNode
  }

  it should "write JSON without optional fields correctly" in {
    val path = getClass.getResource("bidrequest_without_option.json").getPath
    val expectedJson = (Source fromFile path).mkString
    val expectedJsonNode = mapper.readTree(expectedJson)

    val imp = ImpBuilder("id").build
    val bidRequest = BidRequestBuilder("req", Seq(imp)).build
    val actualJson = writer.write(bidRequest)
    val actualJsonNode = mapper.readTree(actualJson)

    actualJsonNode shouldBe expectedJsonNode
  }
}
