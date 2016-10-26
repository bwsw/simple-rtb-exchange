package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.Site
import com.bitworks.rtb.model.request.builder.ContentBuilder
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.builder.SiteBuilder SiteBuilder]].
  *
  * @author Egor Ilchenko
  */
class SiteBuilderTest extends FlatSpec with Matchers {

  "SiteBuilder" should "build Site correctly" in {
    val site = Site(
      1001,
      Some(Seq("sectioncat")),
      Some(Seq("pagecat")),
      Some("page"),
      Some("ref"),
      Some("search"),
      Some(1),
      Some(ContentBuilder().build))

    val builder = SiteBuilder(site.id)
    site.sectionCat.foreach(sectionCat => builder.withSectionCat(sectionCat))
    site.pageCat.foreach(pageCat => builder.withPageCat(pageCat))
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
      1001,
      None,
      None,
      None,
      None,
      None,
      None,
      None)

    val builtSite = SiteBuilder(site.id).build

    builtSite shouldBe site
  }

}
