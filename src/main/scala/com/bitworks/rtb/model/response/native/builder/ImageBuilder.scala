package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Image

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Image Image]].
  *
  * @author Egor Ilchenko
  */
class ImageBuilder(url: String) {
  private var w: Option[Int] = None
  private var h: Option[Int] = None
  private var ext: Option[Any] = None

  def withW(i: Int) = {
    w = Some(i)
    this
  }

  def withH(i: Int) = {
    h = Some(i)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Image */
  def build = Image(url, w, h, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Image Image]].
  *
  * @author Egor Ilchenko
  */
object ImageBuilder {
  def apply(url: String) = new ImageBuilder(url)
}
