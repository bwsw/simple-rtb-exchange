package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.{Bid, SeatBid}

/**
  * Builder for [[com.bitworks.rtb.model.response.SeatBid]]
  *
  * @author Egor Ilchenko
  *
  */
protected class SeatBidBuilder(bid: Seq[Bid]) {
  private var seat: Option[String] = None
  private var group: Int = 0
  private var ext: Option[Any] = None

  def withSeat(s: String) = {
    seat = Some(s)
    this
  }

  def withGroup(i: Int) = {
    group = i
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns SeatBid */
  def build = SeatBid(bid, seat, group, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.SeatBid]]
  *
  * @author Egor Ilchenko
  *
  */
object SeatBidBuilder {
  def apply(bid: Seq[Bid]): SeatBidBuilder = new SeatBidBuilder(bid)
}
