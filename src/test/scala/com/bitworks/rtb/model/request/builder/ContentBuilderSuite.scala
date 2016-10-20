package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Content, Producer}
import org.scalatest.FunSuite

/** Test for [[com.bitworks.rtb.model.request.builder.ContentBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ContentBuilderSuite extends FunSuite {

  test("Test building Content without optional arguments") {
    assert(
      ContentBuilder().build ===
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
        None))
  }

  test("Test building Content with optional arguments") {
    val p = Producer(None, None, None, None, None)

    assert(
      ContentBuilder()
        .withId("123")
        .withEpisode(5)
        .withTitle("title")
        .withSeries("auto")
        .withSeason("s3")
        .withProducer(p)
        .withUrl("content.com")
        .withCat(Seq("IAB1-3", "IAB5-7"))
        .withVideoQuality(2)
        .withContext(1)
        .withContentRating("MPAA")
        .withUserRating("middle")
        .withQagMediaRating(2)
        .withKeyWords("kw1,kw2")
        .isLiveStream(true)
        .withSourceRelationship(1)
        .withLen(30)
        .withLanguage("ru")
        .withEmbeddable(false)
        .withExt("ext")
        .build ===
        Content(
          Some("123"),
          Some(5),
          Some("title"),
          Some("auto"),
          Some("s3"),
          Some(p),
          Some("content.com"),
          Some(Seq("IAB1-3", "IAB5-7")),
          Some(2),
          Some(1),
          Some("MPAA"),
          Some("middle"),
          Some(2),
          Some("kw1,kw2"),
          Some(true),
          Some(1),
          Some(30),
          Some("ru"),
          Some(false),
          Some("ext")))
  }

}
