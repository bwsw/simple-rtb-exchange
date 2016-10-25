package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Link

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Link Link]].
  *
  * @author Egor Ilchenko
  */
class LinkBuilder(url: String) {
  private var clickTrackers: Option[Seq[String]] = None
  private var fallback: Option[String] = None
  private var ext: Option[Any] = None

  def withClickTrackers(s: Seq[String]) = {
    clickTrackers = Some(s)
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
  def build = Link(url, clickTrackers, fallback, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Link Link]].
  *
  * @author Egor Ilchenko
  */
object LinkBuilder {
  def apply(url: String) = new LinkBuilder(url)
}
