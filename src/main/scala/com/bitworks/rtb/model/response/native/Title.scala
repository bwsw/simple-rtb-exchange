package com.bitworks.rtb.model.response.native

/**
  * Corresponds to the Title Object in the request, with the value filled in.
  *
  * @param text the text associated with the text element
  * @param ext  placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Title(
  text: String,
  ext: Option[Any])
