package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Title
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.TitleBuilder]]
  *
  * @author Pavel Tomskikh
  *
  */
class TitleBuilderTest extends FlatSpec with Matchers {

  "TitleBuilder" should "build Title with default parameters correctly" in {
    val title = Title(123, None)
    val buildedTitle = TitleBuilder(123).build

    buildedTitle shouldBe title
  }

  it should "build Title with optional parameters correctly" in {
    val title = Title(123, Some("ext"))
    val buildedTitle = TitleBuilder(123).withExt("ext").build

    buildedTitle shouldBe title
  }
}
