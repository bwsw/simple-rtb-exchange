package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native._

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Asset]].
  *
  * @param id is required for [[com.bitworks.rtb.model.request.native.Asset]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class AssetBuilder private (id: Int) {
  private var required: Option[Int] = Some(0)
  private var title: Option[Title] = None
  private var img: Option[Image] = None
  private var video: Option[Video] = None
  private var data: Option[Data] = None
  private var ext: Option[Any] = None

  def withRequired(i: Int) = {
    required = Some(i)
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

object AssetBuilder {
  def apply(id: Int) = new AssetBuilder(id)
}
