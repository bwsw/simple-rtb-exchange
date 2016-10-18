package com.bitworks.rtb.model.request.native_adv

import com.bitworks.rtb.model.constant.ImageAssetType._

/** Used for all image elements of the Native ad such as Icons, Main Image, etc.
  *
  * @param `type` type ID of the image element supported by the publisher
  * @param w      width of the image in pixels
  * @param wMin   minimum requested width of the image in pixels
  * @param h      height of the image in pixels
  * @param hMin   minimum requested height of the image in pixels
  * @param mimes  whitelist of content MIME types supported
  * @param ext    a placeholder for exchange-specific extensions to OpenRTB
  */
case class Image(
                  `type`: Option[ImageAssetType],
                  w: Option[Int],
                  wMin: Option[Int],
                  h: Option[Int],
                  hMin: Option[Int],
                  mimes: Option[Seq[String]],
                  ext: Option[Any])
