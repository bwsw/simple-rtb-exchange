package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Title
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.native.builder.TitleBuilder TitleBuilder]].
  *
  * @author Egor Ilchenko
  */
class TitleBuilderTest extends FlatSpec with Matchers {

  "TitleBuilder" should "build Title correctly" in {
    val title = Title("text", Some("string"))

    val builder = TitleBuilder(title.text)
    title.ext.foreach(ext => builder.withExt(ext))

    val builtTitle = builder.build

    builtTitle shouldBe title
  }

  it should "build Title with default values correctly" in {
    val title = Title("text", None)

    val builtTitle = TitleBuilder(title.text).build

    builtTitle shouldBe title
  }

}
