package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Deal, Pmp}

/** Builder for Pmp model
  *
  * Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class PmpBuilder private {
  private var private_auction: Option[Int] = None
  private var deals: Option[Seq[Deal]] = None
  private var ext: Option[Any] = None

  def withPrivateAuction(i: Int) = {
    private_auction = Some(i)
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
  def build = Pmp(private_auction, deals, ext)
}

/** Builder for Pmp model */
object PmpBuilder {
  def apply(): PmpBuilder = new PmpBuilder
}