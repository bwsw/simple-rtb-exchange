package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Data
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.DataBuilder DataBuilder]].
  *
  * @author Egor Ilchenko
  *
  */
class DataBuilderTest extends FlatSpec with Matchers{

  "DataBuilder" should "build Data correctly" in {
    val data = Data(Some("id"), Some("name"),
      Some(Seq.empty), Some("ext"))

    val builtData = DataBuilder()
        .withId("id")
        .withName("name")
        .withSegment(Seq.empty)
        .withExt("ext")
        .build

    builtData shouldBe data
  }

}
