package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Data
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.native.builder.DataBuilder DataBuilder]].
  *
  * @author Egor Ilchenko
  */
class DataBuilderTest extends FlatSpec with Matchers {

  "DataBuilder" should "build Data correctly" in {
    val data = Data(Some("label"), "value", Some("string"))

    val builder = DataBuilder(data.value)
    data.label.foreach(label => builder.withLabel(label))
    data.ext.foreach(ext => builder.withExt(ext))

    val builtData = builder.build

    builtData shouldBe data
  }


}
