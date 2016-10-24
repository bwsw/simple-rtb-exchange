package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Site, Content, Publisher}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.SiteBuilder SiteBuilder]].
  *
  * @author Pavel Tomskikh
  */
class SiteBuilderTest extends FlatSpec with Matchers {

  "SiteBuilder" should "build Site with default values correctly" in {
    val site =
      Site(
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None)
    val builtSite = SiteBuilder().build

    builtSite shouldBe site
  }

  it should "build Site correctly" in {
    val publisher =
      Publisher(
        None,
        None,
        None,
        None,
        None)
    val content =
      Content(
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None,
        None)
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

    var builder = SiteBuilder()
    site.id.foreach(id => builder = builder.withId(id))
    site.name.foreach(name => builder = builder.withName(name))
    site.domain.foreach(domain => builder = builder.withDomain(domain))
    site.cat.foreach(cat => builder = builder.withCat(cat))
    site.sectionCat.foreach(sectionCat => builder = builder.withSectionCat(sectionCat))
    site.pageCat.foreach(pageCat => builder = builder.withPageCat(pageCat))
    site.page.foreach(page => builder = builder.withPage(page))
    site.ref.foreach(ref => builder = builder.withRef(ref))
    site.search.foreach(search => builder = builder.withSearch(search))
    site.mobile.foreach(mobile => builder = builder.withMobile(mobile))
    site.privacyPolicy.foreach(privacyPolicy => builder = builder.withPrivacyPolicy(privacyPolicy))
    site.publisher.foreach(publisher => builder = builder.withPublisher(publisher))
    site.content.foreach(content => builder = builder.withContent(content))
    site.keywords.foreach(keywords => builder = builder.withKeywords(keywords))
    site.ext.foreach(ext => builder = builder.withExt(ext))

    val builtSite = builder.build

    builtSite shouldBe site
  }
}
