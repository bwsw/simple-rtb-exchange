package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{App, Content, Publisher}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.AppBuilder AppBuilder]].
  *
  * @author Pavel Tomskikh
  */
class AppBuilderTest extends FlatSpec with Matchers {

  "AppBuilder" should "build App with default values correctly" in {
    val app =
      App(
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
    val builtApp = AppBuilder().build

    builtApp shouldBe app
  }

  it should "build App correctly" in {
    val publisher = Publisher(None, None, None, None, None)
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

    var builder = AppBuilder()
    app.id.foreach(id => builder = builder.withId(id))
    app.name.foreach(name => builder = builder.withName(name))
    app.bundle.foreach(bundle => builder = builder.withBundle(bundle))
    app.domain.foreach(domain => builder = builder.withDomain(domain))
    app.storeUrl.foreach(storeUrl => builder = builder.withStoreUrl(storeUrl))
    app.cat.foreach(cat => builder = builder.withCat(cat))
    app.sectionCat.foreach(sectionCat => builder = builder.withSectionCat(sectionCat))
    app.pageCat.foreach(pageCat => builder = builder.withPageCat(pageCat))
    app.ver.foreach(ver => builder = builder.withVer(ver))
    app.privacyPolicy.foreach(privacyPolicy => builder = builder.withPrivacyPolicy(privacyPolicy))
    app.paid.foreach(paid => builder = builder.withPaid(paid))
    app.publisher.foreach(publisher => builder = builder.withPublisher(publisher))
    app.content.foreach(content => builder = builder.withContent(content))
    app.keywords.foreach(keywords => builder = builder.withKeywords(keywords))
    app.ext.foreach(ext => builder = builder.withExt(ext))

    val builtApp = builder.build

    builtApp shouldBe app
  }
}
