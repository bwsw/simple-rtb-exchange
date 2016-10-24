package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Data
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.DataBuilder DataBuilder]].
  *
  * @author Egor Ilchenko
  */
class DataBuilderTest extends FlatSpec with Matchers {

  "DataBuilder" should "build Data correctly" in {
    val data = Data(
      Some("id"),
      Some("name"),
      Some(Seq.empty),
      Some("ext"))

    val builder = DataBuilder()
    data.id.foreach(id => builder.withId(id))
    data.name.foreach(name => builder.withName(name))
    data.segment.foreach(segment => builder.withSegment(segment))
    data.ext.foreach(ext => builder.withExt(ext))

    val builtData = builder.build

    builtData shouldBe data
  }

}
