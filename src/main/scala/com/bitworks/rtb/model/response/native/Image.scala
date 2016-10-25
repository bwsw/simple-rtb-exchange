package com.bitworks.rtb.model.response.native

/**
  * Information about image elements of the Native ad.
  * The Image object to be used for all image elements of the Native ad
  * such as Icons, Main Image, etc.
  *
  * @param url URL of the image asset
  * @param w   width of the image in pixels
  * @param h   height of the image in pixels
  * @param ext placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Image(
    url: String,
    w: Option[Int],
    h: Option[Int],
    ext: Option[Any])
