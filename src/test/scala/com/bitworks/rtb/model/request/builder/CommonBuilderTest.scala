package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Data, Regs, Segment}
import org.scalatest.{FlatSpec, Matchers}

class CommonBuilderTest extends FlatSpec with Matchers{

  "RegsBuilder" should "build Regs correctly" in {
    val regs = Regs(42, Some("ext"))

    val buildedRegs = RegsBuilder.builder
      .withCoppa(42)
      .withExt("ext")
      .build

    buildedRegs shouldBe regs
  }

  "SegmentBuilder" should "build Segment correctly" in {
    val segment = Segment(Some("id"), Some("name"), Some("value"), Some("ext"))

    val buildedSegment = SegmentBuilder.builder
      .withId("id")
      .withName("name")
      .withValue("value")
      .withExt("ext")
      .build

    buildedSegment shouldBe segment
  }

  "DataBuilder" should "build Data correctly" in {
    val data = Data(Some("id"), Some("name"),
      Some(Seq.empty), Some("ext"))

    val buildedData = DataBuilder.builder
      .withId("id")
      .withName("name")
      .withSegment(Seq.empty)
      .withExt("ext")
      .build

    buildedData shouldBe data
  }


}
