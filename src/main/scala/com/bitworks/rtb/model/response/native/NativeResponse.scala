package com.bitworks.rtb.model.response.native

/**
  * Top level JSON object which identifies a native response.
  *
  * @param ver         version of the Native Markup version in use
  * @param assets      list of native ad’s assets
  * @param link        destination Link. This is default link object for the ad
  * @param imptrackers array of impression tracking URLs
  * @param jstracker   javaScript impression tracker
  * @param ext         placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class NativeResponse(
  ver: Int,
  assets: Seq[Asset],
  link: Link,
  imptrackers: Option[Seq[String]],
  jstracker: Option[String],
  ext: Option[Any])
