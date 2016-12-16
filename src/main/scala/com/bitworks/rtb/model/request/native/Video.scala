package com.bitworks.rtb.model.request.native

/**
  * Information about video elements supported in the Native Ad.
  *
  * @param mimes       content MIME types supported
  * @param minDuration minimum video ad duration in seconds
  * @param maxDuration maximum video ad duration in seconds
  * @param protocols   video protocols the publisher can accept in the bid response
  * @param ext         a placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  */
case class Video(
    mimes: Seq[String],
    minDuration: Int,
    maxDuration: Int,
    protocols: Seq[Int],
    ext: Option[Any])
