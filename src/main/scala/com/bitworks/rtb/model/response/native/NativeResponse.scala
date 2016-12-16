package com.bitworks.rtb.model.response.native

/**
  * Top level object which identifies a native response.
  *
  * @param ver         version of the Native Markup version in use
  * @param assets      list of native ad’s assets
  * @param link        destination [[com.bitworks.rtb.model.response.native.Link Link]].
  *                    This is default link object for the ad
  * @param impTrackers array of impression tracking URLs
  * @param jsTracker   javaScript impression tracker
  * @param ext         placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class NativeResponse(
    ver: Int,
    assets: Seq[Asset],
    link: Link,
    impTrackers: Option[Seq[String]],
    jsTracker: Option[String],
    ext: Option[Any])
