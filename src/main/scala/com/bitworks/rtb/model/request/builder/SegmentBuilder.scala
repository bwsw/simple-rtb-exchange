package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Segment

/** Builder for Segment model
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
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

/** Builder for Segment model  */
object SegmentBuilder {
  def apply() = new SegmentBuilder
}
