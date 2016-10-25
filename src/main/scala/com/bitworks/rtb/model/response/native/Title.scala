package com.bitworks.rtb.model.response.native

/**
  * Information about title element of the Native ad, with the value filled in.
  *
  * @param text the text associated with the text element
  * @param ext  placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Title(
    text: String,
    ext: Option[Any])
