package com.bitworks.rtb.request.builder

import com.bitworks.rtb.request.Publisher
import org.scalatest.FunSuite

/** Test for [[com.bitworks.rtb.request.builder.PublisherBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class PublisherBuilderSuite extends FunSuite {

  test("Test building Publisher without optional arguments") {
    assert(PublisherBuilder().build === Publisher(None, None, None, None, None))
  }

  test("Test building Publisher with optional arguments") {
    assert(
      PublisherBuilder()
        .withId("123")
        .withName("pub")
        .withCat(Seq("IAB1-2"))
        .withDomain("pub.com")
        .withExt("ext")
        .build ===
        Publisher(
          Some("123"),
          Some("pub"),
          Some(Seq("IAB1-2")),
          Some("pub.com"),
          Some("ext")))
  }

}
