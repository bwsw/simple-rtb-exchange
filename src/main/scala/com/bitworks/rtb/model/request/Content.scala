package com.bitworks.rtb.model.request

/**
  * Information about content in which the impression will appear.
  *
  * @param id                 ID uniquely identifying the content
  * @param episode            episode number
  * @param title              content title
  * @param series             content series
  * @param season             content season
  * @param producer           details about the content Producer
  * @param url                URL of the content, for buy-side contextualization or review
  * @param cat                IAB content categories that describe the content producer
  * @param videoQuality       video quality per IAB’s classification
  * @param context            type of content
  * @param contentRating      content rating
  * @param userRating         user rating of the content
  * @param qagMediaRating     media rating per QAG guidelines
  * @param keyWords           comma separated list of keywords describing the content
  * @param liveStream         indicates if content is live
  * @param sourceRelationship 0 = indirect, 1 = direct
  * @param len                length of content in seconds
  * @param language           content language using ISO-639-1-alpha-2
  * @param embeddable         indicates if content is embeddable
  * @param ext                placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  *
  */
case class Content(
    id: Option[String],
    episode: Option[Int],
    title: Option[String],
    series: Option[String],
    season: Option[String],
    producer: Option[Producer],
    url: Option[String],
    cat: Option[Seq[String]],
    videoQuality: Option[Int],
    context: Option[Int],
    contentRating: Option[String],
    userRating: Option[String],
    qagMediaRating: Option[Int],
    keyWords: Option[String],
    liveStream: Option[Int],
    sourceRelationship: Option[Int],
    len: Option[Int],
    language: Option[String],
    embeddable: Option[Int],
    ext: Option[Any])
