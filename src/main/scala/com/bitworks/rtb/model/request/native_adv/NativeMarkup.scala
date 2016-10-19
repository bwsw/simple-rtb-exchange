package com.bitworks.rtb.model.request.native_adv

import com.bitworks.rtb.model.constant.ImageAssetType.ImageAssetType
import com.bitworks.rtb.model.constant.DataAssetType.DataAssetType

/** Defines the native advertising opportunity available for bid via this bid
  * request. It must be included directly in the impression object if the
  * impression offered for auction is a native ad format.
  *
  * @param ver      version of the Native Markup
  * @param layout   the Layout ID of the native ad unit
  * @param adUnit   the Ad unit ID of the native ad unit
  * @param plcmtCnt the number of identical placements in this Layout
  * @param seq      ad number
  * @param assets   an array of Asset Objects
  * @param ext      a placeholder for exchange-specific extensions to OpenRTB
  */
case class NativeMarkup(
                         ver: Option[String],
                         layout: Option[DataAssetType],
                         adUnit: Option[ImageAssetType],
                         plcmtCnt: Option[Int],
                         seq: Option[Int],
                         assets: Seq[Asset],
                         ext: Option[Any])
