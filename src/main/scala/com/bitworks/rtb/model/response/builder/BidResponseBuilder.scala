package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.{BidResponse, SeatBid}

/** Builder for BidResponse model
  *
  * Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class BidResponseBuilder private(id: String, seatbid: Seq[SeatBid]){
  private var bidid: Option[String] = None
  private var cur: String = "USD"
  private var customData: Option[String] = None
  private var nbr: Option[Int] = None
  private var ext: Option[Any] = None

  def withBidId(s: String) = { bidid = Some(s); this }
  def withCur(s: String) = { cur = s; this }
  def withCustomData(s: String) = { customData = Some(s); this }
  def withNbr(i: Int) = { nbr = Some(i); this }
  def withExt(a: Any) = { ext = Some(a); this }

  /** Returns BidResponse */
  def build = BidResponse(id, seatbid, bidid, cur, customData, nbr, ext)
}

/** Builder for BidResponse model  */
object BidResponseBuilder{
  def apply(id: String, seatbid: Seq[SeatBid]): BidResponseBuilder = new BidResponseBuilder(id, seatbid)
}
