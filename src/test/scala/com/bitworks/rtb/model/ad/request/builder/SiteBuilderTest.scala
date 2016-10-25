package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.Site
import com.bitworks.rtb.model.request.builder.ContentBuilder
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.Site Site]].
  *
  * @author Egor Ilchenko
  */
class SiteBuilderTest extends FlatSpec with Matchers {

  "SiteBuilder" should "build Site correctly" in {
    val site = Site(
      Some("id"),
      Some(Seq("sectioncat")),
      Some(Seq("pagecat")),
      Some("page"),
      Some("ref"),
      Some("search"),
      Some(1),
      Some(ContentBuilder().build))

    val builder = SiteBuilder()
    site.id.foreach(id => builder.withId(id))
    site.sectioncat.foreach(sectioncat => builder.withSectioncat(sectioncat))
    site.pagecat.foreach(pagecat => builder.withPagecat(pagecat))
    site.page.foreach(page => builder.withPage(page))
    site.ref.foreach(ref => builder.withRef(ref))
    site.search.foreach(search => builder.withSearch(search))
    site.mobile.foreach(mobile => builder.withMobile(mobile))
    site.content.foreach(content => builder.withContent(content))

    val builtSite = builder.build

    builtSite shouldBe site
  }

  it should "build Site with default values correctly" in {
    val site = Site(
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

}
