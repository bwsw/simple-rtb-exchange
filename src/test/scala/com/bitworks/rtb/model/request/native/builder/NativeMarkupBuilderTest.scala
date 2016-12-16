package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.{Asset, NativeMarkup}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for
  * [[com.bitworks.rtb.model.request.native.builder.NativeMarkupBuilder NativeMarkupBuilder]].
  *
  * @author Pavel Tomskikh
  */
class NativeMarkupBuilderTest extends FlatSpec with Matchers {

  val assets = Seq(
    Asset(31, 1, None, None, None, None, None),
    Asset(22, 0, None, None, None, None, None))

  "NativeMarkupBuilder" should "build NativeMarkup with default values correctly" in {
    val nativeMarkup = NativeMarkup(
      NativeMarkupBuilder.Ver,
      None,
      None,
      NativeMarkupBuilder.plcmtCnt,
      NativeMarkupBuilder.Seq,
      assets,
      None)
    val builtNativeMarkup = NativeMarkupBuilder(nativeMarkup.assets).build

    builtNativeMarkup shouldBe nativeMarkup
  }

  it should "build NativeMarkup correctly" in {
    val nativeMarkup = NativeMarkup(
      "2.7",
      Some(3),
      Some(2),
      5,
      2,
      assets,
      Some("ext"))

    var builder = NativeMarkupBuilder(nativeMarkup.assets)
        .withVer(nativeMarkup.ver)
        .withPlcmtCnt(nativeMarkup.plcmtCnt)
        .withSeq(nativeMarkup.seq)
    nativeMarkup.layout.foreach(layout => builder = builder.withLayout(layout))
    nativeMarkup.adUnit.foreach(adUnit => builder = builder.withAdUnit(adUnit))
    nativeMarkup.ext.foreach(ext => builder = builder.withExt(ext))

    val builtNativeMarkup = builder.build

    builtNativeMarkup shouldBe nativeMarkup
  }
}
