package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Image
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.ImageBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ImageBuilderTest extends FlatSpec with Matchers {

  "ImageBuilder" should "build Image with default parameters correctly" in {
    val image = Image(None, None, None, None, None, None, None)
    val buildedImage = ImageBuilder().build

    buildedImage shouldBe image
  }

  it should "build Image with optional parameters correctly" in {
    val image = Image(
      Some(1),
      Some(100),
      Some(50),
      Some(80),
      Some(20),
      Some(Seq("image/jpg")),
      Some("ext"))
    val buildedImage = ImageBuilder()
        .withType(1)
        .withW(100)
        .withWmin(50)
        .withH(80)
        .withHmin(20)
        .withMimes(Seq("image/jpg"))
        .withExt("ext")
        .build

    buildedImage shouldBe image
  }

}
