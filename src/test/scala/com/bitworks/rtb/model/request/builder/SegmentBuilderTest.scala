package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Segment
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.SegmentBuilder SegmentBuilder]].
  *
  * @author Egor Ilchenko
  */
class SegmentBuilderTest extends FlatSpec with Matchers {

  "SegmentBuilder" should "build Segment correctly" in {
    val segment = Segment(Some("id"), Some("name"), Some("value"), Some("ext"))

    val builder = SegmentBuilder()
    segment.id.foreach(id => builder.withId(id))
    segment.name.foreach(name => builder.withName(name))
    segment.value.foreach(value => builder.withValue(value))
    segment.ext.foreach(ext => builder.withExt(ext))

    val builtSegment = builder.build

    builtSegment shouldBe segment
  }

  it should "build Segment with default values correctly" in {
    val segment = Segment(None, None, None, None)

    val builtSegment = SegmentBuilder().build

    builtSegment shouldBe segment
  }

}
