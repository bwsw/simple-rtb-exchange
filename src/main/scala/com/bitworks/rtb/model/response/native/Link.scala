package com.bitworks.rtb.model.response.native

/**
  * Used for ‘call to action’ assets, or other links from the Native ad.
  *
  * @param url           landing URL of the clickable link
  * @param clicktrackers list of third-party tracker URLs to be fired on click of the URL
  * @param fallback      fallback URL for deeplink. To be used if the URL given in url is not
  *                      supported by the device.
  * @param ext           placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Link(
  url: String,
  clicktrackers: Option[Seq[String]],
  fallback: Option[String],
  ext: Option[Any])
