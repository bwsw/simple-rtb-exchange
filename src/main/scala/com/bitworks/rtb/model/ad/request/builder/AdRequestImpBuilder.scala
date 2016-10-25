package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.AdRequestImp
import com.bitworks.rtb.model.request.{Banner, Native, Video}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.AdRequestImp AdRequestImp]]
  *
  * @author Egor Ilchenko
  */
class AdRequestImpBuilder(id: String) {
  private var banner: Option[Banner] = None
  private var video: Option[Video] = None
  private var native: Option[Native] = None

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

  /** Returns [[com.bitworks.rtb.model.ad.request.AdRequestImp AdRequestImp]] */
  def build = AdRequestImp(id, banner, video, native)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.AdRequestImp AdRequestImp]]
  *
  * @author Egor Ilchenko
  */
object AdRequestImpBuilder {
  def apply(id: String) = new AdRequestImpBuilder(id)

}
