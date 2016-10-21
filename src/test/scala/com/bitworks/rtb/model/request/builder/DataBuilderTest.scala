package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Data
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.Data]]
  *
  * @author Egor Ilchenko
  *
  */
class DataBuilderTest extends FlatSpec with Matchers{

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
