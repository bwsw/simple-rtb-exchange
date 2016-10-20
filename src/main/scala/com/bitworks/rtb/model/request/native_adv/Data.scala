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
  * Used for all non-core elements of the native unit such as Ratings,
  * Review Count, Stars, Download count, descriptions etc.
  *
  * @param `type` type ID of the element supported by the publisher
  *               See the Table 7.3 Data Asset Types in
  *               OpenRTB Native Ads API Specification Version 1.0.0.2 for details.
  * @param len    maximum length of the text in the element’s response
  * @param ext    a placeholder for exchange-specific extensions to OpenRTB
  */
case class Data(
    `type`: Int,
    len: Option[Int],
    ext: Option[Any])
