package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.Image

/**
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Builder for [[com.bitworks.rtb.model.request.native_adv.Image]]
  */
class ImageBuilder private {
  private var `type`: Option[Int] = None
  private var w: Option[Int] = None
  private var wMin: Option[Int] = None
  private var h: Option[Int] = None
  private var hMin: Option[Int] = None
  private var mimes: Option[Seq[String]] = None
  private var ext: Option[Any] = None

  def withType(i: Int) = { `type` = Some(i); this }
  def withW(i: Int) = { w = Some(i); this }
  def withWMin(i: Int) = { wMin = Some(i); this }
  def withH(i: Int) = { h = Some(i); this }
  def withHMin(i: Int) = { hMin = Some(i); this }
  def withMimes(s: Seq[String]) = { mimes = Some(s); this }
  def withExt(a: Any) = { ext = Some(a); this }

  def build = Image(`type`, w, wMin, h, hMin, mimes, ext)
}

object ImageBuilder {
  def apply() = new ImageBuilder
}
