package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Publisher
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.PublisherBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class PublisherBuilderTest extends FlatSpec with Matchers {

  "PublisherBuilder" should "build Publisher with default parameters correctly" in {
    val publisher = Publisher(None, None, None, None, None)
    val buildedPublisher = PublisherBuilder().build

    buildedPublisher shouldBe publisher
  }

  it should "build Publisher with optional parameters correctly" in {
    val publisher = Publisher(
      Some("123"),
      Some("pub"),
      Some(Seq("IAB1-2")),
      Some("pub.com"),
      Some("ext"))
    val buildedPublisher = PublisherBuilder()
        .withId("123")
        .withName("pub")
        .withCat(Seq("IAB1-2"))
        .withDomain("pub.com")
        .withExt("ext")
        .build

    buildedPublisher shouldBe publisher
  }

}
