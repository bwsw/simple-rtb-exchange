package com.bitworks.rtb.model.request.native

/**
  * Information about title element of the Native ad.
  *
  * @param len maximum length of the text in the title element
  * @param ext a placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  *
  */
case class Title(
    len: Int,
    ext: Option[Any])
