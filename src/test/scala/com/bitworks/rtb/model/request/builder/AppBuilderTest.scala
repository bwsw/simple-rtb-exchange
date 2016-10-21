package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{App, Content, Publisher}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.AppBuilder]]
  *
  * @author Pavel Tomskikh
  *
  */
class AppBuilderTest extends FlatSpec with Matchers {

  "AppBuilder" should "build App with default parameters correctly" in {
    val app = App(None, None, None, None, None, None, None, None,
      None, None, None, None, None, None, None)
    val buildedApp = AppBuilder().build

    buildedApp shouldBe app
  }

  it should "build App with optional parameters correctly" in {
    val publisher = Publisher(None, None, None, None, None)
    val content = Content(None, None, None, None, None, None, None, None,
      None, None, None, None, None, None, None, None, None, None, None, None)
    val app = App(
      Some("123"),
      Some("prod"),
      Some("bundle"),
      Some("app.com"),
      Some("app.com/app"),
      Some(Seq("IAB1-2")),
      Some(Seq("IAB3-4")),
      Some(Seq("IAB2-7")),
      Some("1.4.2"),
      Some(1),
      Some(0),
      Some(publisher),
      Some(content),
      Some("kw1,kw2"),
      Some("ext"))
    val buildedApp = AppBuilder()
        .withId("123")
        .withName("prod")
        .withBundle("bundle")
        .withDomain("app.com")
        .withStoreUrl("app.com/app")
        .withCat(Seq("IAB1-2"))
        .withSectionCat(Seq("IAB3-4"))
        .withPageCat(Seq("IAB2-7"))
        .withVer("1.4.2")
        .withPrivacyPolicy(1)
        .withPaid(0)
        .withPublisher(publisher)
        .withContent(content)
        .withKeyWords("kw1,kw2")
        .withExt("ext")
        .build

    buildedApp shouldBe app
  }

}
