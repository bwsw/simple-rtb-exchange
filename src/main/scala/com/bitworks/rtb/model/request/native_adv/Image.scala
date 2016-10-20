package com.bitworks.rtb.model.request.native_adv

/**
  *
  * Created on: 10/18/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Used for all image elements of the Native ad such as Icons, Main Image, etc.
  *
  * @param `type` type ID of the image element supported by the publisher
  *               See the Table 7.4 Image Asset Types in
  *               OpenRTB Native Ads API Specification Version 1.0.0.2 for details.
  * @param w      width of the image in pixels
  * @param wMin   minimum requested width of the image in pixels
  * @param h      height of the image in pixels
  * @param hMin   minimum requested height of the image in pixels
  * @param mimes  whitelist of content MIME types supported
  * @param ext    a placeholder for exchange-specific extensions to OpenRTB
  */
case class Image(
    `type`: Option[Int],
    w: Option[Int],
    wMin: Option[Int],
    h: Option[Int],
    hMin: Option[Int],
    mimes: Option[Seq[String]],
    ext: Option[Any])
