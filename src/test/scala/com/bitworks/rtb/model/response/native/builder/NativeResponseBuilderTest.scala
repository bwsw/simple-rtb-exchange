package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.NativeResponse
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.native.builder.NativeResponseBuilder NativeResponseBuilder]].
  *
  * @author Egor Ilchenko
  */
class NativeResponseBuilderTest extends FlatSpec with Matchers {

  "NativeResponseBuilder" should "build NativeResponse correctly" in {
    val nativeResponse = NativeResponse(
      1,
      Seq(AssetBuilder(1).build),
      LinkBuilder("url").build,
      Some(Seq("tracker")),
      Some("jstracker"),
      Some("string"))

    val builder = NativeResponseBuilder(nativeResponse.assets, nativeResponse.link)
      .withVer(nativeResponse.ver)
    nativeResponse.impTrackers.foreach(imptrackers => builder.withImpTrackers(imptrackers))
    nativeResponse.jsTracker.foreach(jstracker => builder.withJsTracker(jstracker))
    nativeResponse.ext.foreach(ext => builder.withExt(ext))

    val builtNativeResponse = builder.build

    builtNativeResponse shouldBe nativeResponse
  }

  it should "build NativeResponse with default values correctly" in {
    val nativeResponse = NativeResponse(
      NativeResponseBuilder.Ver,
      Seq(AssetBuilder(1).build),
      LinkBuilder("url").build,
      None,
      None,
      None)

    val builtNativeResponse = NativeResponseBuilder(
      nativeResponse.assets,
      nativeResponse.link)
      .build

    builtNativeResponse shouldBe nativeResponse
  }

}
