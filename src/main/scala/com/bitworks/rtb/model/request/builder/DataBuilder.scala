package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Data, Segment}

object DataBuilder {
  protected class DataBuilder{
    private var id: Option[String] = None
    private var name: Option[String] = None
    private var segment: Option[Seq[Segment]] = None
    private var ext: Option[Any] = None

    def withId(s: String) = { id = Some(s); this }
    def withName(s: String) = { name = Some(s); this }
    def withSegment(s: Seq[Segment]) = { segment = Some(s); this }
    def withExt(a: Any) = { ext = Some(a); this }

    /** Returns Data */
    def build = Data(id, name, segment, ext)
  }
  /** Returns builder for Data */
  def builder = new DataBuilder
}
