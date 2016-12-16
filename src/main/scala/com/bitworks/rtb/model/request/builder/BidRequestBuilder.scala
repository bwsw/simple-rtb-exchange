package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request._

/**
  * Builder for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @param id  value of id in [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object
  * @param imp value of imp in [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object
  * @author Pavel Tomskikh
  */
class BidRequestBuilder private(id: String, imp: Seq[Imp]) {
  private var site: Option[Site] = None
  private var app: Option[App] = None
  private var device: Option[Device] = None
  private var user: Option[User] = None
  private var test: Int = BidRequestBuilder.Test
  private var at: Int = BidRequestBuilder.At
  private var tmax: Option[Int] = None
  private var wseat: Option[Seq[String]] = None
  private var allImps: Int = BidRequestBuilder.AllImps
  private var cur: Option[Seq[String]] = None
  private var bcat: Option[Seq[String]] = None
  private var badv: Option[Seq[String]] = None
  private var regs: Option[Regs] = None
  private var ext: Option[Any] = None

  def withSite(s: Site) = {
    site = Some(s)
    this
  }

  def withApp(a: App) = {
    app = Some(a)
    this
  }

  def withDevice(d: Device) = {
    device = Some(d)
    this
  }

  def withUser(u: User) = {
    user = Some(u)
    this
  }

  def withTest(i: Int) = {
    test = i
    this
  }

  def withAt(i: Int) = {
    at = i
    this
  }

  def withTmax(i: Int) = {
    tmax = Some(i)
    this
  }

  def withWseat(s: Seq[String]) = {
    wseat = Some(s)
    this
  }

  def withAllImps(i: Int) = {
    allImps = i
    this
  }

  def withCur(s: Seq[String]) = {
    cur = Some(s)
    this
  }

  def withBcat(s: Seq[String]) = {
    bcat = Some(s)
    this
  }

  def withBadv(s: Seq[String]) = {
    badv = Some(s)
    this
  }

  def withRegs(r: Regs) = {
    regs = Some(r)
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
    tmax,
    wseat,
    allImps,
    cur,
    bcat,
    badv,
    regs,
    ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @author Pavel Tomskikh
  */
object BidRequestBuilder {
  val Test = 0
  val At = 2
  val AllImps = 0

  def apply(id: String, imp: Seq[Imp]) = new BidRequestBuilder(id, imp)
}
