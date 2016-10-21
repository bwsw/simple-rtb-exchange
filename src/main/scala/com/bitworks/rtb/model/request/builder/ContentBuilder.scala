package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Content, Producer}

/**
  * Builder for [[com.bitworks.rtb.model.request.Content]]
  *
  * Created on: 10/20/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ContentBuilder private {
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
  private var liveStream: Option[Int] = None
  private var sourceRelationship: Option[Int] = None
  private var len: Option[Int] = None
  private var language: Option[String] = None
  private var embeddable: Option[Int] = None
  private var ext: Option[Any] = None

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withEpisode(i: Int) = {
    episode = Some(i)
    this
  }

  def withTitle(s: String) = {
    title = Some(s)
    this
  }

  def withSeries(s: String) = {
    series = Some(s)
    this
  }

  def withSeason(s: String) = {
    season = Some(s)
    this
  }

  def withProducer(p: Producer) = {
    producer = Some(p)
    this
  }

  def withUrl(s: String) = {
    url = Some(s)
    this
  }

  def withCat(s: Seq[String]) = {
    cat = Some(s)
    this
  }

  def withVideoQuality(i: Int) = {
    videoQuality = Some(i)
    this
  }

  def withContext(i: Int) = {
    context = Some(i)
    this
  }

  def withContentRating(s: String) = {
    contentRating = Some(s)
    this
  }

  def withUserRating(s: String) = {
    userRating = Some(s)
    this
  }

  def withQagMediaRating(i: Int) = {
    qagMediaRating = Some(i)
    this
  }

  def withKeyWords(s: String) = {
    keyWords = Some(s)
    this
  }

  def withLiveStream(i: Int) = {
    liveStream = Some(i)
    this
  }

  def withSourceRelationship(i: Int) = {
    sourceRelationship = Some(i)
    this
  }

  def withLen(i: Int) = {
    len = Some(i)
    this
  }

  def withLanguage(s: String) = {
    language = Some(s)
    this
  }

  def withEmbeddable(i: Int) = {
    embeddable = Some(i)
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
