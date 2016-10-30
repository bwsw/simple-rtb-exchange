package com.bitworks.rtb.model.response.native

/**
  * Information about video elements supported in the Native Ad, yet containing a value of a
  * conforming VAST tag as a value.
  *
  * @param vastTag VAST xml
  * @author Egor Ilchenko
  */
case class Video(vastTag: String)
