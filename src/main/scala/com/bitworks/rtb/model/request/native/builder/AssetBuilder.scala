package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native._

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Asset Asset]] object.
  *
  * @param id value of id in [[com.bitworks.rtb.model.request.native.Asset Asset]] object
  * @author Pavel Tomskikh
  */
class AssetBuilder private (id: Int) {
  private var required: Int = AssetBuilder.Required
  private var title: Option[Title] = None
  private var img: Option[Image] = None
  private var video: Option[Video] = None
  private var data: Option[Data] = None
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

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Asset(id, required, title, img, video, data, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Asset Asset]] object.
  *
  * @author Pavel Tomskikh
  */
object AssetBuilder {
  val Required = 0

  def apply(id: Int) = new AssetBuilder(id)
}
