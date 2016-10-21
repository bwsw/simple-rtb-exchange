package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Deal, Pmp}

/**
  * Builder for [[com.bitworks.rtb.model.request.Pmp]]
  *
  * @author Egor Ilchenko
  *
  */
class PmpBuilder private {
  private var privateAuction: Option[Int] = None
  private var deals: Option[Seq[Deal]] = None
  private var ext: Option[Any] = None

  def withPrivateAuction(i: Int) = {
    privateAuction = Some(i)
    this
  }

  def withDeals(i: Seq[Deal]) = {
    deals = Some(i)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Pmp */
  def build = Pmp(privateAuction, deals, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.Pmp]]
  *
  * @author Egor Ilchenko
  *
  */
object PmpBuilder {
  def apply(): PmpBuilder = new PmpBuilder
}