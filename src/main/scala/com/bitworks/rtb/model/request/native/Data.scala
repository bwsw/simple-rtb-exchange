package com.bitworks.rtb.model.request.native

/**
  * Information about non-core elements of the native unit such as Ratings, descriptions etc.
  *
  * @param `type` type ID of the element supported by the publisher
  * @param len    maximum length of the text in the element’s response
  * @param ext    a placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/18/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
case class Data(
    `type`: Int,
    len: Option[Int],
    ext: Option[Any])
