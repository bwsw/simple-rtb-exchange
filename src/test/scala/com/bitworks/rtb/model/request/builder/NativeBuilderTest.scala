package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Native
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.NativeBuilder NativeBuilder]].
  *
  * @author Egor Ilchenko
  */
class NativeBuilderTest extends FlatSpec with Matchers {

  "NativeBuilder" should "build Native correctly" in {
    val native = Native(
      "request",
      Some("ver"),
      Some(Seq(1)),
      Some(Seq(2)),
      Some("string"))

    val builder = NativeBuilder(native.request)
    native.ver.foreach(ver => builder.withVer(ver))
    native.api.foreach(api => builder.withApi(api))
    native.battr.foreach(battr => builder.withBattr(battr))
    native.ext.foreach(ext => builder.withExt(ext))

    val builtNative = builder.build

    builtNative shouldBe native
  }

}
