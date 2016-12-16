package com.bitworks.rtb.model.request

/**
  * An in-stream video impression.
  *
  * @param mimes          content MIME types supported
  * @param minDuration    minimum video ad duration in seconds
  * @param maxDuration    maximum video ad duration in seconds
  * @param protocol       supported video bid response protocol; deprecated
  * @param protocols      array of supported video bid response protocols
  * @param w              width of the video player in pixels
  * @param h              height of the video player in pixels
  * @param startDelay     indicates the start delay in seconds for pre-roll, mid-roll,
  *                       or post-roll ad placements
  * @param linearity      indicates if the impression must be linear, nonlinear, etc.
  *                       If none specified, assume all are allowed.
  * @param sequence       sequence number to coordinate delivery of muplitple creatives if multiple
  *                       ad impressions are offered in the same bid request
  * @param battr          blocked creative attributes
  * @param maxExtended    maximum extended video ad duration if extension is allowed.
  *                       If blank or 0, extension is not allowed. If -1, extension is
  *                       allowed, and there is no time limit imposed. If greater than 0,
  *                       then the value represents the number of seconds of extended
  *                       play supported beyond the maxduration value.
  * @param minBitrate     minimum bit rate in Kbps
  * @param maxBitrate     maximum bit rate in Kbps
  * @param boxingAllowed  indicates if letter-boxing of 4:3 content into a 16:9 window is
  *                       allowed, where 0 = no, 1 = yes
  * @param playbackMethod allowed playback methods
  * @param delivery       supported delivery methods (e.g., streaming, progressive)
  * @param pos            ad position on screen
  * @param companionAd    array of [[com.bitworks.rtb.model.request.Banner Banner]] objects if
  *                       companion ads are available
  * @param api            list of supported API frameworks for this impression
  * @param companionType  supported VAST companion ad types
  * @param ext            placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Video(
    mimes: Seq[String],
    minDuration: Option[Int],
    maxDuration: Option[Int],
    protocol: Option[Int],
    protocols: Option[Seq[Int]],
    w: Option[Int],
    h: Option[Int],
    startDelay: Option[Int],
    linearity: Option[Int],
    sequence: Option[Int],
    battr: Option[Seq[Int]],
    maxExtended: Option[Int],
    minBitrate: Option[Int],
    maxBitrate: Option[Int],
    boxingAllowed: Int,
    playbackMethod: Option[Seq[Int]],
    delivery: Option[Seq[Int]],
    pos: Option[Int],
    companionAd: Option[Seq[Banner]],
    api: Option[Seq[Int]],
    companionType: Option[Seq[Int]],
    ext: Option[Any])
