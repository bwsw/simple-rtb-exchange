package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.{Bid, SeatBid}

/** Builder for SeatBid model  */
protected class SeatBidBuilder(bid: Seq[Bid]){
  private var seat: Option[String] = None
  private var group: Int = 0
  private var ext: Option[Any] = None

  def withSeat(s: String) = { seat = Some(s); this }
  def withGroup(i: Int) = { group = i; this }
  def withExt(s: Any) = { ext = Some(s); this }

  /** Returns SeatBid */
  def build = SeatBid(bid, seat, group, ext)
}

/** Builder for SeatBid model  */
object SeatBidBuilder{
  def apply(bid: Seq[Bid]): SeatBidBuilder = new SeatBidBuilder(bid)
}
