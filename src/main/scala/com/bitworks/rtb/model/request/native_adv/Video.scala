package com.bitworks.rtb.model.request.native_adv

import com.bitworks.rtb.model.constant.VideoBidResponseProtocols

/** Used for all video elements supported in the Native Ad.
  *
  * @param mimes       content MIME types supported
  * @param minDuration minimum video ad duration in seconds
  * @param maxDuration maximum video ad duration in seconds
  * @param protocols   video protocols the publisher can accept in the bid response
  * @param ext         a placeholder for exchange-specific extensions to OpenRTB
  */
case class Video(
                  mimes: Option[Seq[String]],
                  minDuration: Option[Int],
                  maxDuration: Option[Int],
                  protocols: Option[Seq[VideoBidResponseProtocols.Value]],
                  ext: Option[Any])
