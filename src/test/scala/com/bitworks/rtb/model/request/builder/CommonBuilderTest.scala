package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Data, Regs, Segment}
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class CommonBuilderTest extends FlatSpec with Matchers{

  "RegsBuilder" should "build Regs correctly" in {
    val regs = Regs(42, Some("ext"))

    val buildedRegs = RegsBuilder()
      .withCoppa(42)
      .withExt("ext")
      .build

    buildedRegs shouldBe regs
  }

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

  "DataBuilder" should "build Data correctly" in {
    val data = Data(Some("id"), Some("name"),
      Some(Seq.empty), Some("ext"))

    val buildedData = DataBuilder()
      .withId("id")
      .withName("name")
      .withSegment(Seq.empty)
      .withExt("ext")
      .build

    buildedData shouldBe data
  }


}
