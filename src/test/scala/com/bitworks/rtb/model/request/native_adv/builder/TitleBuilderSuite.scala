package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.Title
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
  * Test for [[com.bitworks.rtb.model.request.native_adv.builder.TitleBuilder]]
  */
class TitleBuilderSuite extends FunSuite {
  test("Test building Title without optional arguments") {
    assert(TitleBuilder(123).build === Title(123, None))
  }

  test("Test building Title with optional arguments") {
    assert(TitleBuilder(123).withExt("ext").build === Title(123, Some("ext")))
  }
}
