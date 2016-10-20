package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request._

/** Builder for [[com.bitworks.rtb.model.request.BidRequest]]
  *
  * Created on: 10/20/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class BidRequestBuilder private (id: String, imp: Seq[Imp]) {
  private var site: Option[Site] = None
  private var app: Option[App] = None
  private var device: Option[Device] = None
  private var user: Option[User] = None
  private var test: Option[Int] = Some(0)
  private var at: Option[Int] = Some(2)
  private var tMax: Option[Int] = None
  private var wSeat: Option[Seq[String]] = None
  private var allImps: Option[Int] = Some(0)
  private var cur: Option[Seq[String]] = None
  private var bCat: Option[Seq[String]] = None
  private var badv: Option[Seq[String]] = None
  private var regs: Option[Regs] = None
  private var ext: Option[Any] = None

  def withSite(a: Site) = {
    site = Some(a)
    this
  }

  def withApp(a: App) = {
    app = Some(a)
    this
  }

  def withDevice(a: Device) = {
    device = Some(a)
    this
  }

  def withUser(a: User) = {
    user = Some(a)
    this
  }

  def withTest(a: Int) = {
    test = Some(a)
    this
  }

  def withAt(a: Int) = {
    at = Some(a)
    this
  }

  def withTMax(a: Int) = {
    tMax = Some(a)
    this
  }

  def withWSeat(a: Seq[String]) = {
    wSeat = Some(a)
    this
  }

  def withAllImps(a: Int) = {
    allImps = Some(a)
    this
  }

  def withCur(a: Seq[String]) = {
    cur = Some(a)
    this
  }

  def withBCat(a: Seq[String]) = {
    bCat = Some(a)
    this
  }

  def withBadv(a: Seq[String]) = {
    badv = Some(a)
    this
  }

  def withRegs(a: Regs) = {
    regs = Some(a)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = BidRequest(    
    id,
    imp,
    site,
    app,
    device,
    user,
    test,
    at,
    tMax,
    wSeat,
    allImps,
    cur,
    bCat,
    badv,
    regs,
    ext)
}

object BidRequestBuilder {
  def apply(id: String, imp: Seq[Imp]) = new BidRequestBuilder(id, imp)
}
