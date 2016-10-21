package com.bitworks.rtb.model.request.native

/**
  * Information about non-core elements of the native unit such as Ratings, descriptions etc.
  *
  * @param `type` type ID of the element supported by the publisher
  * @param len    maximum length of the text in the element’s response
  * @param ext    a placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  *
  */
case class Data(
    `type`: Int,
    len: Option[Int],
    ext: Option[Any])
