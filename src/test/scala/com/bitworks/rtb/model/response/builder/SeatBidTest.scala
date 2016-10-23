package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.SeatBid
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.builder.SeatBidBuilder SeatBidBuilder]].
  *
  * @author Egor Ilchenko
  */
class SeatBidTest extends FlatSpec with Matchers {

  "SeatBidBuilder" should "build SeatBid correctly" in {
    val seatBid = SeatBid(Seq.empty, Some("seat"), 1, Some("string"))

    var builder = SeatBidBuilder(seatBid.bid).withGroup(seatBid.group)
    seatBid.seat.foreach(seat => builder = builder.withSeat(seat))
    seatBid.ext.foreach(ext => builder = builder.withExt(ext))

    val builtSeatBid = builder.build

    builtSeatBid shouldBe seatBid
  }

  "SeatBidBuilder" should "build SeatBid with default values correctly" in {
    val seatBid = SeatBid(Seq.empty, None, SeatBidBuilder.Group, None)

    val builtSeatBid = SeatBidBuilder(seatBid.bid).build

    builtSeatBid shouldBe seatBid
  }

}
