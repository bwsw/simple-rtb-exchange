package com.bitworks.rtb.model.request.native_adv

import com.bitworks.rtb.model.constant.DataAssetType._

/** Used for all non-core elements of the native unit such as Ratings,
  * Review Count, Stars, Download count, descriptions etc.
  *
  * @param `type` type ID of the element supported by the publisher
  * @param len    maximum length of the text in the element’s response
  * @param ext    a placeholder for exchange-specific extensions to OpenRTB
  */
case class Data(
                 `type`: DataAssetType,
                 len: Option[Int],
                 ext: Option[Any])
