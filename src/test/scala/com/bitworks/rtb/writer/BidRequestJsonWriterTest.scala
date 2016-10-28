package com.bitworks.rtb.writer

import com.bitworks.rtb.writer.BidRequestJsonWriter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[BidRequestJsonWriter BidRequestJsonWriter]]
  *
  * @author Pavel Tomskikh
  */
class BidRequestJsonWriterTest extends FlatSpec with Matchers {

  val mapper = new ObjectMapper

  "BidRequestJsonWriter" should "generate JSON string for BidRequest object correctly" in {
    new BidRequestExample {
      val expected = mapper.readValue(bidRequestJson, classOf[ObjectNode])
      val writedString = BidRequestJsonWriter.write(bidRequest)
      val writedJson = mapper.readValue(writedString, classOf[ObjectNode])

      writedJson shouldBe expected
    }
  }
}
