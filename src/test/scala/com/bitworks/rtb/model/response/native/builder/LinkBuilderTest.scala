package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Link
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.native.builder.LinkBuilder LinkBuilder]].
  *
  * @author Egor Ilchenko
  */
class LinkBuilderTest extends FlatSpec with Matchers {

  "LinkBuilder" should "build Link correctly" in {
    val link = Link(
      "url",
      Some(Seq("tracker")),
      Some("fallback"),
      Some("string"))

    val builder = LinkBuilder(link.url)
    link.clickTrackers.foreach(clcktrck => builder.withClickTrackers(clcktrck))
    link.fallback.foreach(fallback => builder.withFallback(fallback))
    link.ext.foreach(ext => builder.withExt(ext))

    val builtLink = builder.build

    builtLink shouldBe link
  }

  it should "build Link with default values correctly" in {
    val link = Link(
      "url",
      None,
      None,
      None)

    val builtLink = LinkBuilder(link.url).build

    builtLink shouldBe link
  }

}
