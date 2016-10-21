package com.bitworks.rtb.model.request.native

/**
  * Information about title element of the Native ad.
  *
  * @param len maximum length of the text in the title element
  * @param ext a placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/18/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
case class Title(
    len: Int,
    ext: Option[Any])
