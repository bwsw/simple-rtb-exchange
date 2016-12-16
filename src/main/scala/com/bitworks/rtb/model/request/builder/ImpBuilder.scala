package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request._

/**
  * Builder for [[com.bitworks.rtb.model.request.Imp Imp]].
  *
  * @param id value of id in [[com.bitworks.rtb.model.request.Imp Imp]] object
  * @author Egor Ilchenko
  */
class ImpBuilder private(id: String) {
  private var banner: Option[Banner] = None
  private var video: Option[Video] = None
  private var native: Option[Native] = None
  private var displayManager: Option[String] = None
  private var displayManagerVer: Option[String] = None
  private var instl: Int = ImpBuilder.Instl
  private var tagId: Option[String] = None
  private var bidFloor: BigDecimal = ImpBuilder.BidFloor
  private var bidFloorCur: String = ImpBuilder.BidFloorCur
  private var secure: Option[Int] = None
  private var iframeBuster: Option[Seq[String]] = None
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

  def withDisplayManager(s: String) = {
    displayManager = Some(s)
    this
  }

  def withDisplayManagerVer(s: String) = {
    displayManagerVer = Some(s)
    this
  }

  def withInstl(i: Int) = {
    instl = i
    this
  }

  def withTagId(s: String) = {
    tagId = Some(s)
    this
  }

  def withBidFloor(b: BigDecimal) = {
    bidFloor = b
    this
  }

  def withBidFloorCur(s: String) = {
    bidFloorCur = s
    this
  }

  def withSecure(i: Int) = {
    secure = Some(i)
    this
  }

  def withIframeBuster(s: Seq[String]) = {
    iframeBuster = Some(s)
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
  def build = Imp(id, banner, video, native, displayManager, displayManagerVer, instl, tagId,
    bidFloor, bidFloorCur, secure, iframeBuster, pmp, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.Imp Imp]].
  *
  * @author Egor Ilchenko
  */
object ImpBuilder {
  val Instl = 0
  val BidFloor = BigDecimal(0)
  val BidFloorCur = "USD"

  def apply(id: String): ImpBuilder = new ImpBuilder(id)
}
