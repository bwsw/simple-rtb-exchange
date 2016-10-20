package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Banner

/** Builder for Banner model
  *
  * Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  */
class BannerBuilder private {
  private var w: Option[Int] = None
  private var h: Option[Int] = None
  private var wmax: Option[Int] = None
  private var hmax: Option[Int] = None
  private var wmin: Option[Int] = None
  private var hmin: Option[Int] = None
  private var id: Option[String] = None
  private var btype: Option[Seq[Int]] = None
  private var battr: Option[Seq[Int]] = None
  private var pos: Option[Int] = None
  private var mimes: Option[Seq[String]] = None
  private var topframe: Option[Int] = None
  private var expdir: Option[Seq[Int]] = None
  private var api: Option[Seq[Int]] = None
  private var ext: Option[Any] = None

  def withW(i: Int) = {
    w = Some(i)
    this
  }

  def withH(i: Int) = {
    h = Some(i)
    this
  }

  def withWmax(i: Int) = {
    wmax = Some(i)
    this
  }

  def withHmax(i: Int) = {
    hmax = Some(i)
    this
  }

  def withWmin(i: Int) = {
    wmin = Some(i)
    this
  }

  def withHmin(i: Int) = {
    hmin = Some(i)
    this
  }

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withBtype(s: Seq[Int]) = {
    btype = Some(s)
    this
  }

  def withBattr(s: Seq[Int]) = {
    battr = Some(s)
    this
  }

  def withPos(a: Int) = {
    pos = Some(a)
    this
  }

  def withMimes(s: Seq[String]) = {
    mimes = Some(s)
    this
  }

  def withTopframe(i: Int) = {
    topframe = Some(i)
    this
  }

  def withExpdir(s: Seq[Int]) = {
    expdir = Some(s)
    this
  }

  def withApi(s: Seq[Int]) = {
    api = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Banner */
  def build = Banner(w, h, wmax, hmax, wmin, hmin, id, btype, battr, pos, mimes, topframe,
    expdir, api, ext)
}

/** Builder for Banner model  */
object BannerBuilder {
  def apply(): BannerBuilder = new BannerBuilder
}
