package com.bitworks.rtb.request

/** Describes the content in which the impression will appear
  *
  * @param id                 ID uniquely identifying the content
  * @param episode            episode number (typically for video content)
  * @param title              content title
  * @param series             content series
  * @param season             content season (typically for video content)
  * @param producer           details about the content Producer
  * @param url                URL of the content, for buy-side contextualization or review
  * @param cat                IAB content categories that describe the content producer
  *                           See List 5.1 Content Categories in
  *                           OpenRTB API Specification Version 2.3 for details.
  * @param videoQuality       video quality per IAB’s classification
  *                           See List 5.11 Video Quality in
  *                           OpenRTB API Specification Version 2.3 for details.
  * @param context            type of content
  *                           See List 5.14 Content Context in
  *                           OpenRTB API Specification Version 2.3 for details.
  * @param contentRating      content rating
  * @param userRating         user rating of the content
  * @param qagMediaRating     media rating per QAG guidelines
  *                           See List 5.15 QAG Media Ratings in
  *                           OpenRTB API Specification Version 2.3 for details.
  * @param keyWords           comma separated list of keywords describing the content
  * @param liveStream         true if content is live
  * @param sourceRelationship 0 = indirect, 1 = direct
  * @param len                length of content in seconds; appropriate for video or audio
  * @param language           content language using ISO-639-1-alpha-2
  * @param embeddable         true if content is embeddable
  * @param ext                placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/17/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
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
    liveStream: Option[Boolean],
    sourceRelationship: Option[Int],
    len: Option[Int],
    language: Option[String],
    embeddable: Option[Boolean],
    ext: Option[Any])
