package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Site, Content, Publisher}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.SiteBuilder SiteBuilder]].
  *
  * @author Pavel Tomskikh
  *
  */
class SiteBuilderTest extends FlatSpec with Matchers {

  "SiteBuilder" should "build Site with default values correctly" in {
    val site = Site(None, None, None, None, None, None, None, None,
      None, None, None, None, None, None, None)
    val builtSite = SiteBuilder().build

    builtSite shouldBe site
  }

  it should "build Site correctly" in {
    val publisher = Publisher(None, None, None, None, None)
    val content = Content(None, None, None, None, None, None, None, None,
      None, None, None, None, None, None, None, None, None, None, None, None)
    val site = Site(
      Some("123"),
      Some("prod"),
      Some("site.com"),
      Some(Seq("IAB1-2")),
      Some(Seq("IAB3-4")),
      Some(Seq("IAB2-7")),
      Some("pg22"),
      Some("from.com"),
      Some("site"),
      Some(0),
      Some(1),
      Some(publisher),
      Some(content),
      Some("kw1,kw2"),
      Some("ext"))
    val builtSite = SiteBuilder()
        .withId("123")
        .withName("prod")
        .withDomain("site.com")
        .withCat(Seq("IAB1-2"))
        .withSectionCat(Seq("IAB3-4"))
        .withPageCat(Seq("IAB2-7"))
        .withPage("pg22")
        .withRef("from.com")
        .withSearch("site")
        .withMobile(0)
        .withPrivacyPolicy(1)
        .withPublisher(publisher)
        .withContent(content)
        .withKeywords("kw1,kw2")
        .withExt("ext")
        .build

    builtSite shouldBe site
  }

}
