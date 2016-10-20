package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Native
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class NativeBuilderTest extends FlatSpec with Matchers{

  "NativeBuilder" should "build Native correctly" in {
    val native = Native("request", Some("ver"), Some(Seq(1)), Some(Seq(2)), Some("string"))

    val buildedNative = NativeBuilder("request")
        .withVer("ver")
        .withApi(Seq(1))
        .withBattr(Seq(2))
        .withExt("string")
        .build

    buildedNative shouldBe native
  }

}