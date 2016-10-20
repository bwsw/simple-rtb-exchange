package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Native

/** Builder for Native model
  *
  * Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class NativeBuilder private (request: String) {
  private var ver: Option[String] = None
  private var api: Option[Seq[Int]] = None
  private var battr: Option[Seq[Int]] = None
  private var ext: Option[Any] = None

  def withVer(s: String) = {
    ver = Some(s)
    this
  }

  def withApi(s: Seq[Int]) = {
    api = Some(s)
    this
  }

  def withBattr(s: Seq[Int]) = {
    battr = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Native */
  def build = Native(request, ver, api, battr, ext)
}

/** Builder for Native model */
object NativeBuilder{
  def apply(request: String): NativeBuilder = new NativeBuilder(request)
}
