package com.bitworks.rtb.model.response.native

/**
  * Call to action assets or other links from the Native ad.
  *
  * @param url           landing URL of the clickable link
  * @param clickTrackers list of third-party tracker URLs to be fired on click of the URL
  * @param fallback      fallback URL for deeplink. To be used if the URL given in url is not
  *                      supported by the device.
  * @param ext           placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Link(
    url: String,
    clickTrackers: Option[Seq[String]],
    fallback: Option[String],
    ext: Option[Any])
