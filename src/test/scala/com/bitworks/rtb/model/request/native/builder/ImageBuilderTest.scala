package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Image
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.ImageBuilder ImageBuilder]].
  *
  * @author Pavel Tomskikh
  */
class ImageBuilderTest extends FlatSpec with Matchers {

  "ImageBuilder" should "build Image with default values correctly" in {
    val image = Image(None, None, None, None, None, None, None)
    val builtImage = ImageBuilder().build

    builtImage shouldBe image
  }

  it should "build Image correctly" in {
    val image = Image(
      Some(1),
      Some(100),
      Some(50),
      Some(80),
      Some(20),
      Some(Seq("image/jpg")),
      Some("ext"))

    var builder = ImageBuilder()
    image.`type`.foreach(`type` => builder = builder.withType(`type`))
    image.w.foreach(w => builder = builder.withW(w))
    image.wmin.foreach(wmin => builder = builder.withWmin(wmin))
    image.h.foreach(h => builder = builder.withH(h))
    image.hmin.foreach(hmin => builder = builder.withHmin(hmin))
    image.mimes.foreach(mimes => builder = builder.withMimes(mimes))
    image.ext.foreach(ext => builder = builder.withExt(ext))

    val builtImage = builder.build

    builtImage shouldBe image
  }

}
