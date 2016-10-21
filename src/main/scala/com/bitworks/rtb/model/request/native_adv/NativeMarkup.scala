package com.bitworks.rtb.model.request.native_adv

/**
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Defines the native advertising opportunity available for bid via this bid
  * request. It must be included directly in the impression object if the
  * impression offered for auction is a native ad format.
  *
  * @param ver      version of the Native Markup
  * @param layout   the Layout ID of the native ad unit
  *                 See the Table 7.1 Native Layout IDs in
  *                 OpenRTB Native Ads API Specification Version 1.0.0.2 for details.
  * @param adUnit   the Ad unit ID of the native ad unit
  *                 See the Table 7.2 Native Ad Unit IDs in
  *                 OpenRTB Native Ads API Specification Version 1.0.0.2 for details.
  * @param plcmtCnt the number of identical placements in this Layout
  * @param seq      ad number
  * @param assets   an array of Asset Objects
  * @param ext      a placeholder for exchange-specific extensions to OpenRTB
  */
case class NativeMarkup(
    ver: Option[String],
    layout: Option[Int],
    adUnit: Option[Int],
    plcmtCnt: Option[Int],
    seq: Option[Int],
    assets: Seq[Asset],
    ext: Option[Any])
