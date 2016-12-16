package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.BidResponse
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.builder.BidResponseBuilder BidResponseBuilder]].
  *
  * @author Egor Ilchenko
  */
class BidResponseBuilderTest extends FlatSpec with Matchers {

  "BidResponseBuilder" should "build BidResponse with default values" in {
    val bidResponse = BidResponse(
      "id",
      Seq.empty,
      None,
      BidResponseBuilder.Cur,
      None,
      None,
      None)

    val builtBidResponse = BidResponseBuilder(bidResponse.id, bidResponse.seatBid).build

    builtBidResponse shouldBe bidResponse
  }

  it should "build BidResponse correctly" in {
    val bidResponse = BidResponse(
      "id",
      Seq.empty,
      Some("bidid"),
      "EUR",
      Some("custom"),
      Some(1),
      Some("string"))

    var builder = BidResponseBuilder(bidResponse.id, bidResponse.seatBid).withCur(bidResponse.cur)
    bidResponse.bidId.foreach(bidId => builder = builder.withBidId(bidId))
    bidResponse.customData.foreach(customData => builder.withCustomData(customData))
    bidResponse.nbr.foreach(nbr => builder = builder.withNbr(nbr))
    bidResponse.ext.foreach(ext => builder = builder.withExt(ext))

    val builtBidResponse = builder.build

    builtBidResponse shouldBe bidResponse
  }
}
