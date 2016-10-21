package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Data
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.DataBuilder]]
  *
  * @author Pavel Tomskikh
  *
  */
class DataBuilderTest extends FlatSpec with Matchers {

  "DataBuilder" should "build Data with default parameters correctly" in {
    val data = Data(3, None, None)
    val buildedData = DataBuilder(3).build

    buildedData shouldBe data
  }

  it should "build Data with optional parameters correctly" in {
    val data = Data(2, Some(32), Some("ext"))
    val buildedData = DataBuilder(2)
        .withLen(32)
        .withExt("ext")
        .build

    buildedData shouldBe data
  }
}
