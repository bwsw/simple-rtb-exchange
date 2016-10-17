package com.bitworks.rtb.request

/** Describes the content in which the impression will appear
  *
  * @param id ID uniquely identifying the content
  * @param episode episode number (typically for video content)
  * @param title content title
  * @param series content series
  * @param season content season (typically for video content)
  * @param producer details about the content Producer
  * @param url URL of the content, for buy-side contextualization or review
  * @param cat IAB content categories that describe the content producer
  * @param videoQuality video quality per IAB’s classification
  * @param context type of content
  * @param contentRating content rating
  * @param userRating user rating of the content
  * @param qagMediaRating media rating per QAG guidelines
  * @param keyWords comma separated list of keywords describing the content
  * @param liveStream true if content is live
  * @param sourceRelationship 0 = indirect, 1 = direct
  * @param len length of content in seconds; appropriate for video or audio
  * @param language content language using ISO-639-1-alpha-2
  * @param embeddable true if content is embeddable
  */
class Content(
               val id: String,
               val episode: Int,
               val title: String,
               val series: String,
               val season: String,
               val producer: Producer,
               val url: String,
               val cat: Array[String],
               val videoQuality: Int,
               val context: Int,
               val contentRating: String,
               val userRating: String,
               val qagMediaRating: Int,
               val keyWords: String,
               val liveStream: Boolean,
               val sourceRelationship: Int,
               val len: Int,
               val language: String,
               val embeddable: Boolean
             ) {

}
