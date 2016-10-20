package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.{NativeMarkup, Asset}
import org.scalatest.FunSuite

/**
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Test for [[com.bitworks.rtb.model.request.native_adv.builder.NativeMarkupBuilder]]
  */
class NativeMarkupBuilderSuite extends FunSuite {

  val assets = Seq(Asset(31, required = true, None, None, None, None, None))

  test("Test building NativeMarkup without optional arguments") {
    assert(
      NativeMarkupBuilder(assets).build ===
        NativeMarkup(
          Some("1"),
          None,
          None,
          Some(1),
          Some(0),
          assets,
          None))
  }

  test("Test building NativeMarkup with optional arguments") {
    assert(
      NativeMarkupBuilder(assets)
        .withVer("2.7")
        .withLayout(3)
        .withAdUnit(2)
        .withPlcmtCnt(5)
        .withSeq(2)
        .withExt("ext")
        .build ===
        NativeMarkup(
          Some("2.7"),
          Some(3),
          Some(2),
          Some(5),
          Some(2),
          assets,
          Some("ext")))
  }
}
