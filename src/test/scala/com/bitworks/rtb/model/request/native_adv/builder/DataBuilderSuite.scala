package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.Data
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
  * Test for [[com.bitworks.rtb.model.request.native_adv.builder.DataBuilder]]
  */
class DataBuilderSuite extends FunSuite {
  test("Test building Data without optional arguments") {
    assert(DataBuilder(3).build === Data(3, None, None))
  }

  test("Test building Data with optional arguments") {
    assert(
      DataBuilder(2)
        .withLen(32)
        .withExt("ext")
        .build ===
        Data(2, Some(32), Some("ext")))
  }
}
