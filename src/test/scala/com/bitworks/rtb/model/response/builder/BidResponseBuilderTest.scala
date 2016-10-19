package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.constant.NoBidReason
import com.bitworks.rtb.model.response.BidResponse
import org.scalatest.{FlatSpec, Matchers}

class BidResponseBuilderTest extends FlatSpec with Matchers{
  "BidResponseBuilder" should "build BidResponse with required attributes" in {
    val bidResponse = BidResponseBuilder("one", Seq.empty).build

    bidResponse.id shouldBe "one"
    bidResponse.seatbid shouldBe empty
    bidResponse.nbr shouldBe None
  }

  it should "use default value for attribute" in {
    val bidResponse = BidResponseBuilder("one", Seq.empty).build

    bidResponse.cur shouldBe "USD"
  }

  it should "correctly build whole BidResponse" in {
    val bidResponse = BidResponse("id", Seq.empty, Some("bidid"), "EUR",
      Some("custom"), Some(NoBidReason.technicalError), Some("string"))

    val buildedBidResponse = BidResponseBuilder("id", Seq.empty)
      .withBidId("bidid")
      .withCur("EUR")
      .withCustomData("custom")
      .withNbr(NoBidReason.technicalError)
      .withExt("string")
      .build

    buildedBidResponse shouldBe bidResponse
  }

}
