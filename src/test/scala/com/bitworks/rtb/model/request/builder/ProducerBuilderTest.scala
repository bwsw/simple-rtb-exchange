package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Producer
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.ProducerBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ProducerBuilderTest extends FlatSpec with Matchers {

  "ProducerBuilder" should "build Producer with default parameters correctly" in {
    val producer = Producer(None, None, None, None, None)
    val buildedProducer = ProducerBuilder().build

    buildedProducer shouldBe producer
  }

  it should "build Producer with optional parameters correctly" in {
    val producer = Producer(
      Some("123"),
      Some("prod"),
      Some(Seq("IAB1-2")),
      Some("prod.com"),
      Some("ext"))
    val buildedProducer = ProducerBuilder()
        .withId("123")
        .withName("prod")
        .withCat(Seq("IAB1-2"))
        .withDomain("prod.com")
        .withExt("ext")
        .build

    buildedProducer shouldBe producer
  }

}
