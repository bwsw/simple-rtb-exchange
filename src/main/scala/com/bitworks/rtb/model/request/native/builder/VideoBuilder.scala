package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Video

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Video]]
  *
  * @author Pavel Tomskikh
  *
  */
class VideoBuilder private {
  private var mimes: Option[Seq[String]] = None
  private var minDuration: Option[Int] = None
  private var maxDuration: Option[Int] = None
  private var protocols: Option[Seq[Int]] = None
  private var ext: Option[Any] = None

  def withMimes(s: Seq[String]) = {
    mimes = Some(s)
    this
  }

  def withMinDuration(i: Int) = {
    minDuration = Some(i)
    this
  }

  def withMaxDuration(i: Int) = {
    maxDuration = Some(i)
    this
  }

  def withProtocols(s: Seq[Int]) = {
    protocols = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Video(mimes, minDuration, maxDuration, protocols, ext)
}

object VideoBuilder {
  def apply() = new VideoBuilder
}
