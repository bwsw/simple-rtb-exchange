package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.{BidResponse, SeatBid}

/**
  * Builder for [[com.bitworks.rtb.model.response.BidResponse]]
  *
  * @author Egor Ilchenko
  *
  */
class BidResponseBuilder private(id: String, seatbid: Seq[SeatBid]) {
  private var bidId: Option[String] = None
  private var cur: String = "USD"
  private var customData: Option[String] = None
  private var nbr: Option[Int] = None
  private var ext: Option[Any] = None

  def withBidId(s: String) = {
    bidId = Some(s)
    this
  }

  def withCur(s: String) = {
    cur = s
    this
  }

  def withCustomData(s: String) = {
    customData = Some(s)
    this
  }

  def withNbr(i: Int) = {
    nbr = Some(i)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns BidResponse */
  def build = BidResponse(id, seatbid, bidId, cur, customData, nbr, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.BidResponse]]
  *
  * @author Egor Ilchenko
  *
  */
object BidResponseBuilder {
  def apply(id: String, seatbid: Seq[SeatBid]): BidResponseBuilder = new BidResponseBuilder(id, seatbid)
}
