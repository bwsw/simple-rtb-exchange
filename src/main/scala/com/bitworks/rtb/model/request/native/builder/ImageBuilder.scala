package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Image

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Image]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ImageBuilder private {
  private var `type`: Option[Int] = None
  private var w: Option[Int] = None
  private var wmin: Option[Int] = None
  private var h: Option[Int] = None
  private var hmin: Option[Int] = None
  private var mimes: Option[Seq[String]] = None
  private var ext: Option[Any] = None

  def withType(i: Int) = {
    `type` = Some(i)
    this
  }

  def withW(i: Int) = {
    w = Some(i)
    this
  }

  def withWmin(i: Int) = {
    wmin = Some(i)
    this
  }

  def withH(i: Int) = {
    h = Some(i)
    this
  }

  def withHmin(i: Int) = {
    hmin = Some(i)
    this
  }

  def withMimes(s: Seq[String]) = {
    mimes = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Image(`type`, w, wmin, h, hmin, mimes, ext)
}

object ImageBuilder {
  def apply() = new ImageBuilder
}
