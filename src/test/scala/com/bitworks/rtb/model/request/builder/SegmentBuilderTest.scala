package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Segment
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.Segment]]
  *
  * @author Egor Ilchenko
  *
  */
class SegmentBuilderTest extends FlatSpec with Matchers{

  "SegmentBuilder" should "build Segment correctly" in {
    val segment = Segment(Some("id"), Some("name"), Some("value"), Some("ext"))

    val buildedSegment = SegmentBuilder()
        .withId("id")
        .withName("name")
        .withValue("value")
        .withExt("ext")
        .build

    buildedSegment shouldBe segment
  }

}
