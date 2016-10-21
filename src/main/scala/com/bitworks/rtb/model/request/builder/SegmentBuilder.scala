package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Segment

/**
  * Builder for [[com.bitworks.rtb.model.request.Segment]]
  *
  * @author Egor Ilchenko
  *
  */
class SegmentBuilder private {
  private var id: Option[String] = None
  private var name: Option[String] = None
  private var value: Option[String] = None
  private var ext: Option[Any] = None

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withName(s: String) = {
    name = Some(s)
    this
  }

  def withValue(s: String) = {
    value = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Segment */
  def build = Segment(id, name, value, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.Segment]]
  *
  * @author Egor Ilchenko
  *
  */
object SegmentBuilder {
  def apply() = new SegmentBuilder
}
