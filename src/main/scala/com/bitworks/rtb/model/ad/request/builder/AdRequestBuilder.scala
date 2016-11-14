package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request._
import com.bitworks.rtb.model.request.{Device, Regs}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @param imp value of imp in [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
  * @author Egor Ilchenko
  */
class AdRequestBuilder(id: String, imp: Seq[Imp]) {
  private var site: Option[Site] = None
  private var app: Option[App] = None
  private var device: Option[Device] = None
  private var user: Option[User] = None
  private var test: Int = AdRequestBuilder.Test
  private var tmax: Option[Int] = None
  private var regs: Option[Regs] = None

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

  def withTmax(i: Int) = {
    tmax = Some(i)
    this
  }

  def withRegs(r: Regs) = {
    regs = Some(r)
    this
  }

  /** Returns [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] */
  def build = AdRequest(id, imp, site, app, device, user, test, tmax, regs)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @author Egor Ilchenko
  */
object AdRequestBuilder {
  val Test = 0

  def apply(id: String, imp: Seq[Imp]) = new AdRequestBuilder(id, imp)
}
