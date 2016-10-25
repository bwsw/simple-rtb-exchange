package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.Imp
import com.bitworks.rtb.model.request.{Banner, Native, Video}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.Imp Imp]].
  *
  * @param id value of id in [[com.bitworks.rtb.model.ad.request.Imp Imp]]
  * @author Egor Ilchenko
  */
class ImpBuilder(id: String) {
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

  /** Returns [[com.bitworks.rtb.model.ad.request.Imp Imp]] */
  def build = Imp(id, banner, video, native)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.Imp Imp]].
  *
  * @author Egor Ilchenko
  */
object ImpBuilder {
  def apply(id: String) = new ImpBuilder(id)

}
