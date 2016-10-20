package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{App, Content, Publisher}
import org.scalatest.FunSuite

/** Test for [[com.bitworks.rtb.model.request.builder.AppBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class AppBuilderSuite extends FunSuite {

  test("Test building App without optional arguments") {
    assert(AppBuilder().build ===
      App(None, None, None, None, None, None, None, None, None, None, None, None, None, None, None))
  }

  test("Test building App with optional arguments") {
    val p = Publisher(None, None, None, None, None)
    val c = Content(
      None, None, None, None,
      None, None, None, None,
      None, None, None, None,
      None, None, None, None,
      None, None, None, None)
    assert(
      AppBuilder()
        .withId("123")
        .withName("prod")
        .withBundle("bundle")
        .withDomain("app.com")
        .withStoreUrl("app.com/app")
        .withCat(Seq("IAB1-2"))
        .withSectionCat(Seq("IAB3-4"))
        .withPageCat(Seq("IAB2-7"))
        .withVer("1.4.2")
        .isPrivacyPolicy(true)
        .isPaid(false)
        .withPublisher(p)
        .withContent(c)
        .withKeyWords("kw1,kw2")
        .withExt("ext")
        .build === App(
        Some("123"),
        Some("prod"),
        Some("bundle"),
        Some("app.com"),
        Some("app.com/app"),
        Some(Seq("IAB1-2")),
        Some(Seq("IAB3-4")),
        Some(Seq("IAB2-7")),
        Some("1.4.2"),
        Some(true),
        Some(false),
        Some(p),
        Some(c),
        Some("kw1,kw2"),
        Some("ext")))
  }

}
