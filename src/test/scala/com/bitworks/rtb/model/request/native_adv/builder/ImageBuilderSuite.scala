package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.Image
import org.scalatest.FunSuite

/**
  *
  * Created on: 10/19/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Test for [[com.bitworks.rtb.model.request.native_adv.builder.ImageBuilder]]
  */

class ImageBuilderSuite extends FunSuite {
  test("Test building Image without optional arguments") {
    assert(
      ImageBuilder().build === Image(None, None, None, None, None, None, None))
  }

  test("Test building Image with optional arguments") {
    assert(
      ImageBuilder()
        .withType(1)
        .withW(100)
        .withWMin(50)
        .withH(80)
        .withHMin(20)
        .withMimes(Seq("image/jpg"))
        .withExt("ext")
        .build ===
        Image(
          Some(1),
          Some(100),
          Some(50),
          Some(80),
          Some(20),
          Some(Seq("image/jpg")),
          Some("ext")))
  }
}
