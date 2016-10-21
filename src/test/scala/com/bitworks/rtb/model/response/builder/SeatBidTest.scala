package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.SeatBid
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.SeatBid]]
  *
  * @author Egor Ilchenko
  *
  */
class SeatBidTest extends FlatSpec with Matchers{

  "SeatBidBuilder" should "build SeatBid correctly" in {
   val seatBid = SeatBid(Seq.empty, Some("seat"), 1, Some("string"))

   val buildedSeatBid = SeatBidBuilder(Seq.empty)
     .withSeat("seat")
     .withGroup(1)
     .withExt("string")
     .build

   buildedSeatBid shouldBe seatBid
  }
}
