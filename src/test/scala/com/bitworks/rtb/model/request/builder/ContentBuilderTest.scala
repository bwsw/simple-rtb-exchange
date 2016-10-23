package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Content, Producer}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.ContentBuilder ContentBuilder]].
  *
  * @author Pavel Tomskikh
  */
class ContentBuilderTest extends FlatSpec with Matchers {

  "ContentBuilder" should "build Content with default values correctly" in {
    val content = Content(None, None, None, None, None, None, None, None,
      None, None, None, None, None, None, None, None, None, None, None, None)
    val builtContent = ContentBuilder().build

    builtContent shouldBe content
  }

  it should "build Content correctly" in {
    val producer = Producer(None, None, None, None, None)
    val content = Content(
      Some("123"),
      Some(5),
      Some("title"),
      Some("auto"),
      Some("s3"),
      Some(producer),
      Some("content.com"),
      Some(Seq("IAB1-3", "IAB5-7")),
      Some(2),
      Some(1),
      Some("MPAA"),
      Some("middle"),
      Some(2),
      Some("kw1,kw2"),
      Some(1),
      Some(1),
      Some(30),
      Some("ru"),
      Some(0),
      Some("ext"))
    val builtContent = ContentBuilder()
        .withId("123")
        .withEpisode(5)
        .withTitle("title")
        .withSeries("auto")
        .withSeason("s3")
        .withProducer(producer)
        .withUrl("content.com")
        .withCat(Seq("IAB1-3", "IAB5-7"))
        .withVideoQuality(2)
        .withContext(1)
        .withContentRating("MPAA")
        .withUserRating("middle")
        .withQagMediaRating(2)
        .withKeywords("kw1,kw2")
        .withLiveStream(1)
        .withSourceRelationship(1)
        .withLen(30)
        .withLanguage("ru")
        .withEmbeddable(0)
        .withExt("ext")
        .build

    builtContent shouldBe content
  }

}
