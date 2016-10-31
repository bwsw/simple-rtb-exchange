package com.bitworks.rtb.writer

import com.bitworks.rtb.model.request.Imp
import com.bitworks.rtb.model.request.builder.{BidRequestBuilder, ImpBuilder}
import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.writer.BidRequestJsonWriter BidRequestJsonWriter]]
  *
  * @author Pavel Tomskikh
  */
class BidRequestJsonWriterTest extends FlatSpec with Matchers {

  val mapper = new ObjectMapper
  val writer = new BidRequestJsonWriter

  "BidRequestJsonWriter" should "write JSON correctly" in {
    val path = getClass.getResource("bidrequest_with_option.json").getPath
    val expectedJson = (io.Source fromFile path).mkString
    val expectedJsonNode = mapper.readTree(expectedJson)
    val writedJson = writer.write(BidRequestWithOption.bidRequest)
    val writedJsonNode = mapper.readTree(writedJson)

    writedJsonNode shouldBe expectedJsonNode
  }

  it should "write JSON without optional fields correctly" in {
    val path = getClass.getResource("bidrequest_without_option.json").getPath
    val expectedJson = (io.Source fromFile path).mkString
    val expectedJsonNode = mapper.readTree(expectedJson)

    val imp = ImpBuilder("id").build
    val bidRequest = BidRequestBuilder("req", Seq(imp)).build
    val writedJson = writer.write(bidRequest)
    val writedJsonNode = mapper.readTree(writedJson)

    writedJsonNode shouldBe expectedJsonNode
  }
}
