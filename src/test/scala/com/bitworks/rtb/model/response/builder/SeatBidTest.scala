package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.SeatBid
import org.scalatest.{FlatSpec, Matchers}


class SeatBidTest extends FlatSpec with Matchers{
  "SeatBidBuilder" should "build SeatBid with required attributes" in {
    val seatBid = SeatBidBuilder(Seq.empty).build

    seatBid.bid shouldBe empty
  }

 it should "correctly build whole SeatBid" in {
   val seatBid = SeatBid(Seq.empty, Some("seat"), 1, Some("string"))

   val buildedSeatBid = SeatBidBuilder(Seq.empty)
     .withSeat("seat")
     .withGroup(1)
     .withExt("string")
     .build

   buildedSeatBid shouldBe seatBid
  }
}
