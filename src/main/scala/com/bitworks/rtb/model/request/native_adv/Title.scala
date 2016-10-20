package com.bitworks.rtb.model.request.native_adv

/**
  *
  * Created on: 10/18/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Used for title element of the Native ad.
  *
  * @param len maximum length of the text in the title element
  * @param ext a placeholder for exchange-specific extensions to OpenRTB
  */
case class Title(
    len: Int,
    ext: Option[Any])
