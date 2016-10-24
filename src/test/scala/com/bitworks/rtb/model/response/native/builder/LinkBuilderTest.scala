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
    link.clicktrackers.foreach(clcktrck => builder.withClicktrackers(clcktrck))
    link.fallback.foreach(fallback => builder.withFallback(fallback))
    link.ext.foreach(ext => builder.withExt(ext))

    val builtLink = builder.build

    builtLink shouldBe link
  }

}
