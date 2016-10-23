package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Publisher
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.PublisherBuilder PublisherBuilder]].
  *
  * @author Pavel Tomskikh
  *
  */
class PublisherBuilderTest extends FlatSpec with Matchers {

  "PublisherBuilder" should "build Publisher with default values correctly" in {
    val publisher = Publisher(None, None, None, None, None)
    val builtPublisher = PublisherBuilder().build

    builtPublisher shouldBe publisher
  }

  it should "build Publisher correctly" in {
    val publisher = Publisher(
      Some("123"),
      Some("pub"),
      Some(Seq("IAB1-2")),
      Some("pub.com"),
      Some("ext"))
    val builtPublisher = PublisherBuilder()
        .withId("123")
        .withName("pub")
        .withCat(Seq("IAB1-2"))
        .withDomain("pub.com")
        .withExt("ext")
        .build

    builtPublisher shouldBe publisher
  }

}
