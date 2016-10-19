package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Data, Segment}

/** Builder for Data model
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class DataBuilder private {
  private var id: Option[String] = None
  private var name: Option[String] = None
  private var segment: Option[Seq[Segment]] = None
  private var ext: Option[Any] = None

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withName(s: String) = {
    name = Some(s)
    this
  }

  def withSegment(s: Seq[Segment]) = {
    segment = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Data */
  def build = Data(id, name, segment, ext)
}

/** Builder for Data model  */
object DataBuilder {
  def apply() = new DataBuilder
}
