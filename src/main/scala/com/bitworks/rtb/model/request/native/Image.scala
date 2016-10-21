package com.bitworks.rtb.model.request.native

/**
  * Information about image elements of the Native ad.
  *
  * @param `type` type ID of the image element supported by the publisher
  * @param w      width of the image in pixels
  * @param wmin   minimum requested width of the image in pixels
  * @param h      height of the image in pixels
  * @param hmin   minimum requested height of the image in pixels
  * @param mimes  whitelist of content MIME types supported
  * @param ext    a placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  *
  */
case class Image(
    `type`: Option[Int],
    w: Option[Int],
    wmin: Option[Int],
    h: Option[Int],
    hmin: Option[Int],
    mimes: Option[Seq[String]],
    ext: Option[Any])
