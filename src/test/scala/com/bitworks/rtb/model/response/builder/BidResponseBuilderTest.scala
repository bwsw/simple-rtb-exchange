package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.BidResponse
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
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
