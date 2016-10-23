package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Title
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.TitleBuilder TitleBuilder]].
  *
  * @author Pavel Tomskikh
  */
class TitleBuilderTest extends FlatSpec with Matchers {

  "TitleBuilder" should "build Title with default values correctly" in {
    val builtTitle = TitleBuilder(123).build

    builtTitle.ext shouldBe None
  }

  it should "build Title correctly" in {
    val title = Title(123, Some("ext"))

    var builder = TitleBuilder(title.len)
    title.ext.foreach(ext => builder = builder.withExt(ext))

    val builtTitle = builder.build

    builtTitle shouldBe title
  }
}
