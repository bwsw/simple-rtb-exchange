package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Video

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Video Video]].
  *
  * @author Pavel Tomskikh
  */
class VideoBuilder private (
    mimes: Seq[String],
    minDuration: Int,
    maxDuration: Int,
    protocols: Seq[Int]) {
  private var ext: Option[Any] = None

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Video(mimes, minDuration, maxDuration, protocols, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Video Video]].
  *
  * @author Pavel Tomskikh
  */
object VideoBuilder {
  def apply(mimes: Seq[String], minDuration: Int, maxDuration: Int, protocols: Seq[Int]) =
    new VideoBuilder(mimes, minDuration, maxDuration, protocols)
}
