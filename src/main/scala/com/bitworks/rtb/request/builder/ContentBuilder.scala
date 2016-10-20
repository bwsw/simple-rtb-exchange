package com.bitworks.rtb.request.builder

import com.bitworks.rtb.request.{Content, Producer}

/** Builder for [[com.bitworks.rtb.request.Content]]
  *
  * Created on: 10/20/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ContentBuilder {
  private var id: Option[String] = None
  private var episode: Option[Int] = None
  private var title: Option[String] = None
  private var series: Option[String] = None
  private var season: Option[String] = None
  private var producer: Option[Producer] = None
  private var url: Option[String] = None
  private var cat: Option[Seq[String]] = None
  private var videoQuality: Option[Int] = None
  private var context: Option[Int] = None
  private var contentRating: Option[String] = None
  private var userRating: Option[String] = None
  private var qagMediaRating: Option[Int] = None
  private var keyWords: Option[String] = None
  private var liveStream: Option[Boolean] = None
  private var sourceRelationship: Option[Int] = None
  private var len: Option[Int] = None
  private var language: Option[String] = None
  private var embeddable: Option[Boolean] = None
  private var ext: Option[Any] = None

  def withId(a: String) = {
    id = Some(a)
    this
  }

  def withEpisode(a: Int) = {
    episode = Some(a)
    this
  }

  def withTitle(a: String) = {
    title = Some(a)
    this
  }

  def withSeries(a: String) = {
    series = Some(a)
    this
  }

  def withSeason(a: String) = {
    season = Some(a)
    this
  }

  def withProducer(a: Producer) = {
    producer = Some(a)
    this
  }

  def withUrl(a: String) = {
    url = Some(a)
    this
  }

  def withCat(a: Seq[String]) = {
    cat = Some(a)
    this
  }

  def withVideoQuality(a: Int) = {
    videoQuality = Some(a)
    this
  }

  def withContext(a: Int) = {
    context = Some(a)
    this
  }

  def withContentRating(a: String) = {
    contentRating = Some(a)
    this
  }

  def withUserRating(a: String) = {
    userRating = Some(a)
    this
  }

  def withQagMediaRating(a: Int) = {
    qagMediaRating = Some(a)
    this
  }

  def withKeyWords(a: String) = {
    keyWords = Some(a)
    this
  }

  def isLiveStream(a: Boolean) = {
    liveStream = Some(a)
    this
  }

  def withSourceRelationship(a: Int) = {
    sourceRelationship = Some(a)
    this
  }

  def withLen(a: Int) = {
    len = Some(a)
    this
  }

  def withLanguage(a: String) = {
    language = Some(a)
    this
  }

  def withEmbeddable(a: Boolean) = {
    embeddable = Some(a)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Content(
    id,
    episode,
    title,
    series,
    season,
    producer,
    url,
    cat,
    videoQuality,
    context,
    contentRating,
    userRating,
    qagMediaRating,
    keyWords,
    liveStream,
    sourceRelationship,
    len,
    language,
    embeddable,
    ext)
}

object ContentBuilder {
  def apply() = new ContentBuilder
}
