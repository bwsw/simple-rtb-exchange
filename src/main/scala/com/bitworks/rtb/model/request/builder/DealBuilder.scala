package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Banner, Deal}

/** Builder for Deal model
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  */
class DealBuilder private (id: String) {
  private var bidfloor: BigDecimal = 0
  private var bidfloorcur: String = "USD"
  private var at: Option[Int] = None
  private var wseat: Option[Seq[String]] = None
  private var wadomain: Option[Seq[String]] = None
  private var ext: Option[Any] = None

  def withBidfloor(s: String) = {
    bidfloor = BigDecimal(s)
    this
  }

  def withBidfloorcur(s: String) = {
    bidfloorcur = s
    this
  }

  def withAt(i: Int) = {
    at = Some(i)
    this
  }

  def withWseat(s: Seq[String]) = {
    wseat = Some(s)
    this
  }

  def withWadomain(s: Seq[String]) = {
    wadomain = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Deal */
  def build = Deal(id, bidfloor, bidfloorcur, at, wseat, wadomain, ext)
}

/** Builder for Deal model */
object DealBuilder{
  def apply(id: String): DealBuilder = new DealBuilder(id)
}


