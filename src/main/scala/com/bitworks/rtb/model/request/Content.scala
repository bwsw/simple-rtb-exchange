package com.bitworks.rtb.model.request

/**
  * A content in which the impression will appear
  *
  * @param id                 ID uniquely identifying the content
  * @param episode            episode number (typically for video content)
  * @param title              content title
  * @param series             content series
  * @param season             content season (typically for video content)
  * @param producer           details about the content [[com.bitworks.rtb.model.request.Producer
  *                           Producer]]
  * @param url                URL of the content, for buy-side contextualization or review
  * @param cat                IAB content categories that describe the content producer
  * @param videoQuality       video quality per IAB’s classification
  * @param context            type of content
  * @param contentRating      content rating
  * @param userRating         user rating of the content
  * @param qagMediaRating     media rating per QAG guidelines
  * @param keywords           comma separated list of keywords describing the content
  * @param liveStream         indicator whether the content is live, where 0 = not live, 1 = live
  * @param sourceRelationship indicator where source relationship is direct, where the 0 =
  *                           indirect,
  *                           1 = direct
  * @param len                length of content in seconds; appropriate for video or audio
  * @param language           content language using ISO-639-1-alpha-2
  * @param embeddable         indicator whether the content is embeddable, where 0 = no, 1 = yes
  * @param ext                placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskih
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
    keywords: Option[String],
    liveStream: Option[Int],
    sourceRelationship: Option[Int],
    len: Option[Int],
    language: Option[String],
    embeddable: Option[Boolean],
    ext: Option[Any])
