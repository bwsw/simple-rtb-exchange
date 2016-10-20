package com.bitworks.rtb.model.request.native_adv

/**
  *
  * Created on: 10/18/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Used for all video elements supported in the Native Ad.
  *
  * @param mimes       content MIME types supported
  * @param minDuration minimum video ad duration in seconds
  * @param maxDuration maximum video ad duration in seconds
  * @param protocols   video protocols the publisher can accept in the bid response
  *                    See the Table 5.8 Video Bid Response Protocols in
  *                    OpenRTB API Specification Version 2.3 for details.
  * @param ext         a placeholder for exchange-specific extensions to OpenRTB
  */
case class Video(
    mimes: Option[Seq[String]],
    minDuration: Option[Int],
    maxDuration: Option[Int],
    protocols: Option[Seq[Int]],
    ext: Option[Any])
