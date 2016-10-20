package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request._

/** Builder for Imp model
  *
  * Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ImpBuilder private(id: String) {
  private var banner: Option[Banner] = None
  private var video: Option[Video] = None
  private var native: Option[Native] = None
  private var displaymanager: Option[String] = None
  private var displaymanagerver: Option[String] = None
  private var instl: Int = 0
  private var tagid: Option[String] = None
  private var bidfloor: BigDecimal = BigDecimal("0")
  private var bidfloorcur: String = "USD"
  private var secure: Option[Int] = None
  private var iframebuster: Option[Seq[String]] = None
  private var pmp: Option[Pmp] = None
  private var ext: Option[Any] = None

  def withBanner(b: Banner) = {
    banner = Some(b)
    this
  }

  def withVideo(v: Video) = {
    video = Some(v)
    this
  }

  def withNative(n: Native) = {
    native = Some(n)
    this
  }

  def withDisplaymanager(s: String) = {
    displaymanager = Some(s)
    this
  }

  def withDisplaymanagerver(s: String) = {
    displaymanagerver = Some(s)
    this
  }

  def withInstl(i: Int) = {
    instl = i
    this
  }

  def withTagid(s: String) = {
    tagid = Some(s)
    this
  }

  def withBidfloor(b: BigDecimal) = {
    bidfloor = b
    this
  }

  def withBidfloorcur(s: String) = {
    bidfloorcur = s
    this
  }

  def withSecure(i: Int) = {
    secure = Some(i)
    this
  }

  def withIframebuster(s: Seq[String]) = {
    iframebuster = Some(s)
    this
  }

  def withPmp(p: Pmp) = {
    pmp = Some(p)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Imp */
  def build = Imp(id, banner, video, native, displaymanager, displaymanagerver, instl, tagid,
    bidfloor, bidfloorcur, secure, iframebuster, pmp, ext)
}

/** Builder for Imp model */
object ImpBuilder{
  def apply(id: String): ImpBuilder = new ImpBuilder(id)
}
