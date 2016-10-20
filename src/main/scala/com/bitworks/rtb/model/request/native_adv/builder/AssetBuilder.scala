package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv._

/**
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Builder for [[com.bitworks.rtb.model.request.native_adv.Data]].
  * @param id is required for [[com.bitworks.rtb.model.request.native_adv.Data]]
  */
class AssetBuilder private (id: Int) {
  private var required = false
  private var title: Option[Title] = None
  private var img: Option[Image] = None
  private var video: Option[Video] = None
  private var data: Option[Data] = None
  private var ext: Option[Any] = None

  def withRequired(b: Boolean) = { required = b; this }
  def withTitle(t: Title) = { title = Some(t); this }
  def withImg(i: Image) = { img = Some(i); this }
  def withVideo(v: Video) = { video = Some(v); this }
  def withData(d: Data) = { data = Some(d); this }
  def withExt(a: Any) = { ext = Some(a); this }

  def build = Asset(id, required, title, img, video, data, ext)
}

object AssetBuilder {
  def apply(id: Int) = new AssetBuilder(id)
}
