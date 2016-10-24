package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Image
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.native.builder.ImageBuilder ImageBuilder]].
  *
  * @author Egor Ilchenko
  */
class ImageBuilderTest extends FlatSpec with Matchers {

  "ImageBuilder" should "build Image correctly" in {
    val image = Image("url", Some(1), Some(2), Some("string"))

    val builder = ImageBuilder(image.url)
    image.w.foreach(w => builder.withW(w))
    image.h.foreach(h => builder.withH(h))
    image.ext.foreach(ext => builder.withExt(ext))

    val builtImage = builder.build

    builtImage shouldBe image
  }


}
