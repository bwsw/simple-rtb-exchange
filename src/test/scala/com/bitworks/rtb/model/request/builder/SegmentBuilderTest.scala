package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Segment
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.SegmentBuilder SegmentBuilder]].
  *
  * @author Egor Ilchenko
  *
  */
class SegmentBuilderTest extends FlatSpec with Matchers{

  "SegmentBuilder" should "build Segment correctly" in {
    val segment = Segment(Some("id"), Some("name"), Some("value"), Some("ext"))

    val builtSegment = SegmentBuilder()
        .withId("id")
        .withName("name")
        .withValue("value")
        .withExt("ext")
        .build

    builtSegment shouldBe segment
  }

}
