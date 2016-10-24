package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native._

/**
  * Builder for [[com.bitworks.rtb.model.response.native.NativeResponse NativeResponse]].
  *
  * @author Egor Ilchenko
  */
class NativeResponseBuilder(assets: Seq[Asset], link: Link) {
  private var ver: Int = NativeResponseBuilder.Ver
  private var imptrackers: Option[Seq[String]] = None
  private var jstracker: Option[String] = None
  private var ext: Option[Any] = None

  def withVer(i: Int) = {
    ver = i
    this
  }

  def withImptrackers(s: Seq[String]) = {
    imptrackers = Some(s)
    this
  }

  def withJstracker(s: String) = {
    jstracker = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns NativeResponse */
  def build = NativeResponse(ver, assets, link, imptrackers, jstracker, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.NativeResponse NativeResponse]].
  *
  * @author Egor Ilchenko
  */
object NativeResponseBuilder {
  val Ver = 1

  def apply(assets: Seq[Asset], link: Link) = new NativeResponseBuilder(assets, link)
}
