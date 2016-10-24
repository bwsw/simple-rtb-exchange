package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Link

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Link Link]].
  *
  * @author Egor Ilchenko
  */
class LinkBuilder(url: String) {
  private var clicktrackers: Option[Seq[String]] = None
  private var fallback: Option[String] = None
  private var ext: Option[Any] = None

  def withClicktrackers(s: Seq[String]) = {
    clicktrackers = Some(s)
    this
  }

  def withFallback(s: String) = {
    fallback = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Link */
  def build = Link(url, clicktrackers, fallback, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Link Link]].
  *
  * @author Egor Ilchenko
  */
object LinkBuilder {
  def apply(url: String) = new LinkBuilder(url)
}
