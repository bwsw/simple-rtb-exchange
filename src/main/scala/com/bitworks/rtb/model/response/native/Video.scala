package com.bitworks.rtb.model.response.native

/**
  * Corresponds to the Video Object in the request, yet containing
  * a value of a conforming VAST tag as a value.
  *
  * @param vasttag VAST xml
  * @author Egor Ilchenko
  */
case class Video(vasttag: String)
