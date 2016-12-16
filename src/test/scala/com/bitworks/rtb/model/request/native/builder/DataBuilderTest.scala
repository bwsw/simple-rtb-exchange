package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Data
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.DataBuilder DataBuilder]].
  *
  * @author Pavel Tomskikh
  */
class DataBuilderTest extends FlatSpec with Matchers {

  "DataBuilder" should "build Data with default values correctly" in {
    val data = Data(3, None, None)
    val builtData = DataBuilder(data.`type`).build

    builtData shouldBe data
  }

  it should "build Data correctly" in {
    val data = Data(2, Some(32), Some("ext"))

    var builder = DataBuilder(data.`type`)
    data.len.foreach(len => builder = builder.withLen(len))
    data.ext.foreach(ext => builder = builder.withExt(ext))

    val builtData = builder.build

    builtData shouldBe data
  }
}
