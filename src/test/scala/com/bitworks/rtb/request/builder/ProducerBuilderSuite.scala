package com.bitworks.rtb.request.builder

import com.bitworks.rtb.request.Producer
import org.scalatest.FunSuite

/** Test for [[com.bitworks.rtb.request.builder.ProducerBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ProducerBuilderSuite extends FunSuite {

  test("Test building Producer without optional arguments") {
    assert(ProducerBuilder().build === Producer(None, None, None, None, None))
  }

  test("Test building Producer with optional arguments") {
    assert(
      ProducerBuilder()
        .withId("123")
        .withName("prod")
        .withCat(Seq("IAB1-2"))
        .withDomain("prod.com")
        .withExt("ext")
        .build ===
        Producer(
          Some("123"),
          Some("prod"),
          Some(Seq("IAB1-2")),
          Some("prod.com"),
          Some("ext")))
  }

}
