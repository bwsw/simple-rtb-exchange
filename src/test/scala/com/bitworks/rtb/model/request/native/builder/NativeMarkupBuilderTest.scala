package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.{Asset, NativeMarkup}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.NativeMarkupBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class NativeMarkupBuilderTest extends FlatSpec with Matchers {

  val assets = Seq(
    Asset(31, Some(1), None, None, None, None, None),
    Asset(22, Some(0), None, None, None, None, None))

  "NativeMarkupBuilder" should "build NativeMarkup with default parameters correctly" in {
    val nativeMarkup = NativeMarkup(
      Some("1"),
      None,
      None,
      Some(1),
      Some(0),
      assets,
      None)
    val buildedNativeMarkup = NativeMarkupBuilder(assets).build

    buildedNativeMarkup shouldBe nativeMarkup
  }

  it should "build NativeMarkup with optional parameters correctly" in {
    val nativeMarkup = NativeMarkup(
      Some("2.7"),
      Some(3),
      Some(2),
      Some(5),
      Some(2),
      assets,
      Some("ext"))
    val buildedNativeMarkup = NativeMarkupBuilder(assets)
        .withVer("2.7")
        .withLayout(3)
        .withAdUnit(2)
        .withPlcmtCnt(5)
        .withSeq(2)
        .withExt("ext")
        .build

    buildedNativeMarkup shouldBe nativeMarkup
  }
}
