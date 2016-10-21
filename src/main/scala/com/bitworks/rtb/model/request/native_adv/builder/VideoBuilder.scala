package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.Video

/**
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Builder for [[com.bitworks.rtb.model.request.native_adv.Video]]
  */
class VideoBuilder private {
  private var mimes: Option[Seq[String]] = None
  private var minDuration: Option[Int] = None
  private var maxDuration: Option[Int] = None
  private var protocols: Option[Seq[Int]] = None
  private var ext: Option[Any] = None

  def withMimes(s: Seq[String]) = { mimes = Some(s); this }
  def withMinDuration(i: Int) = { minDuration = Some(i); this }
  def withMaxDuration(i: Int) = { maxDuration = Some(i); this }
  def withProtocols(s: Seq[Int]) = { protocols = Some(s); this }
  def withExt(a: Any) = { ext = Some(a); this }

  def build = Video(mimes, minDuration, maxDuration, protocols, ext)
}

object VideoBuilder {
  def apply() = new VideoBuilder
}
