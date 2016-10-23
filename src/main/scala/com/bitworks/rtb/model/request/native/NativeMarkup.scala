package com.bitworks.rtb.model.request.native

/**
  * Information about the native advertising opportunity available for bid via this bid request.
  *
  * @param ver      version of the Native Markup
  * @param layout   the Layout ID of the native ad unit
  * @param adUnit   the Ad unit ID of the native ad unit
  * @param plcmtCnt the number of identical placements in this Layout
  * @param seq      ad number
  * @param assets   an array of [[com.bitworks.rtb.model.request.native.Asset Asset]] objects
  * @param ext      a placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  */
case class NativeMarkup(
    ver: String,
    layout: Option[Int],
    adUnit: Option[Int],
    plcmtCnt: Int,
    seq: Int,
    assets: Seq[Asset],
    ext: Option[Any])
