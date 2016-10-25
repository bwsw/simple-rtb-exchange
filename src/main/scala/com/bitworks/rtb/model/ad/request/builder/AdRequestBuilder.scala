package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request._
import com.bitworks.rtb.model.request.{Device, Regs}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
  *
  * @author Egor Ilchenko
  */
class AdRequestBuilder(imp: Seq[AdRequestImp], device: Device) {
  private var site: Option[Site] = None
  private var app: Option[App] = None
  private var user: Option[User] = None
  private var test: Option[Int] = None
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

  def withUser(u: User) = {
    user = Some(u)
    this
  }

  def withTest(i: Int) = {
    test = Some(i)
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
  def build = AdRequest(imp, site, app, device, user, test, tmax, regs)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
  *
  * @author Egor Ilchenko
  */
object AdRequestBuilder {
  def apply(imp: Seq[AdRequestImp], device: Device) = new AdRequestBuilder(imp, device)
}
