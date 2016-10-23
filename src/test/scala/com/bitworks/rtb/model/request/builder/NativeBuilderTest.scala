package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Native
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.NativeBuilder NativeBuilder]].
  *
  * @author Egor Ilchenko
  *
  */
class NativeBuilderTest extends FlatSpec with Matchers{

  "NativeBuilder" should "build Native correctly" in {
    val native = Native("request", Some("ver"), Some(Seq(1)), Some(Seq(2)), Some("string"))

    val builtNative = NativeBuilder("request")
        .withVer("ver")
        .withApi(Seq(1))
        .withBattr(Seq(2))
        .withExt("string")
        .build

    builtNative shouldBe native
  }

}
