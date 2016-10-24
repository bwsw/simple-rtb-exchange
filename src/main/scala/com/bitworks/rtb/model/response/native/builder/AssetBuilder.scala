package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native._

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Asset Asset]].
  *
  * @author Egor Ilchenko
  */
class AssetBuilder(id: String) {
  private var required: Int = AssetBuilder.Required
  private var title: Option[Title] = None
  private var img: Option[Image] = None
  private var video: Option[Video] = None
  private var data: Option[Data] = None
  private var link: Option[Link] = None
  private var ext: Option[Any] = None

  def withRequired(i: Int) = {
    required = i
    this
  }

  def withTitle(t: Title) = {
    title = Some(t)
    this
  }

  def withImg(i: Image) = {
    img = Some(i)
    this
  }

  def withVideo(v: Video) = {
    video = Some(v)
    this
  }

  def withData(d: Data) = {
    data = Some(d)
    this
  }

  def withLink(l: Link) = {
    link = Some(l)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Asset */
  def build = Asset(id, required, title, img, video, data, link, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Asset Asset]].
  *
  * @author Egor Ilchenko
  */
object AssetBuilder {
  val Required = 0

  def apply(id: String) = new AssetBuilder(id)
}
