package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.{Asset, Image, Title, Video, Data}
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
  * Test for [[com.bitworks.rtb.model.request.native_adv.builder.AssetBuilder]]
  */
class AssetBuilderSuite extends FunSuite {
  test("Test building Asset without optional arguments") {
    assert(
      AssetBuilder(55).build ===
      Asset(55, required = false, None, None, None, None, None))
  }

  test("Test building Asset with optional arguments") {
    val t = Title(123, None)
    val i = Image(None, None, None, None, None, None, None)
    val v = Video(None, None, None, None, None)
    val d = Data(3, None, None)
    assert(
      AssetBuilder(123)
        .withRequired(true)
        .withTitle(t)
        .withImg(i)
        .withVideo(v)
        .withData(d)
        .withExt("ext")
        .build ===
      Asset(
        123,
        required = true,
        Some(t),
        Some(i),
        Some(v),
        Some(d),
        Some("ext")))
  }
}
