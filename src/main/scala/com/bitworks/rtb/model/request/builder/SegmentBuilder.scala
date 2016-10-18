package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Segment

/** Builder for Segment model  */
object SegmentBuilder {
  protected class SegmentBuilder{
    var id: Option[String] = None
    var name: Option[String] = None
    var value: Option[String] = None
    var ext: Option[Any] = None

    def withId(s: String) = { id = Some(s); this }
    def withName(s: String) = { name = Some(s); this }
    def withValue(s: String) = { value = Some(s); this }
    def withExt(a: Any) = { ext = Some(a); this}

    /** Returns Segment */
    def build = Segment(id, name, value, ext)
  }
  /** Returns builder for Segment */
  def builder = new SegmentBuilder
}
