package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.App
import com.bitworks.rtb.model.request.builder.ContentBuilder
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.App App]].
  *
  * @author Egor Ilchenko
  */
class AppBuilderTest extends FlatSpec with Matchers {

  "AppBuilder" should "build App correctly" in {
    val app = App(
      Some("id"),
      Some(Seq("section")),
      Some(Seq("page")),
      Some(ContentBuilder().build))

    val builder = AppBuilder()
    app.id.foreach(id => builder.withId(id))
    app.sectioncat.foreach(sectioncat => builder.withSectioncat(sectioncat))
    app.pagecat.foreach(pagecat => builder.withPagecat(pagecat))
    app.content.foreach(content => builder.withContent(content))

    val builtApp = builder.build

    builtApp shouldBe app
  }

  it should "build App with default values correctly" in {
    val app = App(
      None,
      None,
      None,
      None)

    val builtApp = AppBuilder().build

    builtApp shouldBe app
  }

}
