package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.BidResponse
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.BidResponse]]
  *
  * @author Egor Ilchenko
  *
  */
class BidResponseBuilderTest extends FlatSpec with Matchers{

  "BidResponseBuilder" should "build BidResponse with default values" in {
    val bidResponse = BidResponseBuilder("one", Seq.empty).build

    bidResponse.cur shouldBe "USD"
  }

  it should "build BidResponse correctly" in {
    val bidResponse = BidResponse("id", Seq.empty, Some("bidid"), "EUR",
      Some("custom"), Some(1), Some("string"))

    val buildedBidResponse = BidResponseBuilder("id", Seq.empty)
      .withBidId("bidid")
      .withCur("EUR")
      .withCustomData("custom")
      .withNbr(1)
      .withExt("string")
      .build

    buildedBidResponse shouldBe bidResponse
  }

}
