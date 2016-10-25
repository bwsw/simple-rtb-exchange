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

    var builder = ContentBuilder()
    content.id.foreach(id => builder = builder.withId(id))
    content.episode.foreach(episode => builder = builder.withEpisode(episode))
    content.title.foreach(title => builder = builder.withTitle(title))
    content.series.foreach(series => builder = builder.withSeries(series))
    content.season.foreach(season => builder = builder.withSeason(season))
    content.producer.foreach(producer => builder = builder.withProducer(producer))
    content.url.foreach(url => builder = builder.withUrl(url))
    content.cat.foreach(cat => builder = builder.withCat(cat))
    content.videoQuality.foreach(videoQuality => builder = builder.withVideoQuality(videoQuality))
    content.context.foreach(context => builder = builder.withContext(context))
    content.contentRating.foreach(contentRating =>
      builder = builder.withContentRating(contentRating))
    content.userRating.foreach(userRating => builder = builder.withUserRating(userRating))
    content.qagMediaRating.foreach(qagMediaRating =>
      builder = builder.withQagMediaRating(qagMediaRating))
    content.keywords.foreach(keywords => builder = builder.withKeywords(keywords))
    content.liveStream.foreach(liveStream => builder = builder.withLiveStream(liveStream))
    content.sourceRelationship.foreach(sourceRelationship =>
      builder = builder.withSourceRelationship(sourceRelationship))
    content.len.foreach(len => builder = builder.withLen(len))
    content.language.foreach(language => builder = builder.withLanguage(language))
    content.embeddable.foreach(embeddable => builder = builder.withEmbeddable(embeddable))
    content.ext.foreach(ext => builder = builder.withExt(ext))

    val builtContent = builder.build

    builtContent shouldBe content
  }
}
