package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Producer
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.ProducerBuilder ProducerBuilder]].
  *
  * @author Pavel Tomskikh
  */
class ProducerBuilderTest extends FlatSpec with Matchers {

  "ProducerBuilder" should "build Producer with default values correctly" in {
    val producer = Producer(None, None, None, None, None)
    val builtProducer = ProducerBuilder().build

    builtProducer shouldBe producer
  }

  it should "build Producer correctly" in {
    val producer = Producer(
      Some("123"),
      Some("prod"),
      Some(Seq("IAB1-2")),
      Some("prod.com"),
      Some("ext"))
    val builtProducer = ProducerBuilder()
        .withId("123")
        .withName("prod")
        .withCat(Seq("IAB1-2"))
        .withDomain("prod.com")
        .withExt("ext")
        .build

    builtProducer shouldBe producer
  }

}
