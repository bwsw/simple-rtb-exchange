package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Native

/**
  * Builder for [[com.bitworks.rtb.model.request.Native Native]].
  *
  * @param request value of [[com.bitworks.rtb.model.request.Native Native]] request in object
  * @author Egor Ilchenko
  */
class NativeBuilder private(request: String) {
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

/**
  * Builder for [[com.bitworks.rtb.model.request.Native Native]].
  *
  * @author Egor Ilchenko
  */
object NativeBuilder {
  def apply(request: String): NativeBuilder = new NativeBuilder(request)
}
