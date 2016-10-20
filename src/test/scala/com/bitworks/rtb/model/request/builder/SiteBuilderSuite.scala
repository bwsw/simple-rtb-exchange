package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Content, Publisher, Site}
import org.scalatest.FunSuite

/** Test for [[com.bitworks.rtb.model.request.builder.SiteBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class SiteBuilderSuite extends FunSuite {

  test("Test building Site without optional arguments") {
    assert(SiteBuilder().build ===
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
        None))
  }

  test("Test building Site with optional arguments") {
    val p = Publisher(None, None, None, None, None)
    val c = Content(
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

    assert(
      SiteBuilder()
        .withId("123")
        .withName("prod")
        .withDomain("site.com")
        .withCat(Seq("IAB1-2"))
        .withSectionCat(Seq("IAB3-4"))
        .withPageCat(Seq("IAB2-7"))
        .withPage("pg22")
        .withRef("from.com")
        .withSearch("site")
        .isMobile(false)
        .isPrivacyPolicy(true)
        .withPublisher(p)
        .withContent(c)
        .withKeyWords("kw1,kw2")
        .withExt("ext")
        .build === Site(
        Some("123"),
        Some("prod"),
        Some("site.com"),
        Some(Seq("IAB1-2")),
        Some(Seq("IAB3-4")),
        Some(Seq("IAB2-7")),
        Some("pg22"),
        Some("from.com"),
        Some("site"),
        Some(false),
        Some(true),
        Some(p),
        Some(c),
        Some("kw1,kw2"),
        Some("ext")))
  }

}
