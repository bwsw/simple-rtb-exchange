package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Banner, Deal}

/**
  * Builder for [[com.bitworks.rtb.model.request.Deal Deal]].
  *
  * @param id value of id in [[com.bitworks.rtb.model.request.Deal Deal]] object
  * @author Egor Ilchenko
  */
class DealBuilder private(id: String) {
  private var bidFloor: BigDecimal = DealBuilder.BidFloor
  private var bidFloorCur: String = DealBuilder.BidFloorCur
  private var at: Option[Int] = None
  private var wseat: Option[Seq[String]] = None
  private var wadomain: Option[Seq[String]] = None
  private var ext: Option[Any] = None

  def withBidFloor(b: BigDecimal) = {
    bidFloor = b
    this
  }

  def withBidFloorCur(s: String) = {
    bidFloorCur = s
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
  def build = Deal(id, bidFloor, bidFloorCur, at, wseat, wadomain, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.Deal Deal]].
  *
  * @author Egor Ilchenko
  */
object DealBuilder {
  val BidFloor = BigDecimal(0)
  val BidFloorCur = "USD"

  def apply(id: String): DealBuilder = new DealBuilder(id)
}


