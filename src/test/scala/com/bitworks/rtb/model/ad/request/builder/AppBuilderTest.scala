package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.App
import com.bitworks.rtb.model.request.builder.ContentBuilder
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.builder.AppBuilder AppBuilder]].
  *
  * @author Egor Ilchenko
  */
class AppBuilderTest extends FlatSpec with Matchers {

  "AppBuilder" should "build App correctly" in {
    val app = App(
      1001,
      Some(Seq("section")),
      Some(Seq("page")),
      Some(ContentBuilder().build))

    val builder = AppBuilder(app.id)
    app.sectionCat.foreach(sectionCat => builder.withSectionCat(sectionCat))
    app.pageCat.foreach(pageCat => builder.withPageCat(pageCat))
    app.content.foreach(content => builder.withContent(content))

    val builtApp = builder.build

    builtApp shouldBe app
  }

  it should "build App with default values correctly" in {
    val app = App(
      1001,
      None,
      None,
      None)

    val builtApp = AppBuilder(1001).build

    builtApp shouldBe app
  }

}
