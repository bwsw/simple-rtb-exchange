package com.bitworks.rtb.model.request

/** In-stream video impression.
  *
  * Many of the fields are non-essential for minimally viable transactions,
  * but are included to offer fine control when needed. Video in OpenRTB generally
  * assumes compliance with the VAST standard. As such, the notion of companion
  * ads is supported by optionally including an array of Banner objects
  * (refer to the Banner object in Section 3.2.3) that define these companion ads.
  *
  * The presence of a Video as a subordinate of the Imp object indicates
  * that this impression is offered as a video type impression. At the publisher’s
  * discretion, that same impression may also be offered as banner and/or native by
  * also including as Imp subordinates the Banner and/or Native objects, respectively.
  * However, any given bid for the impression must conform to one of the offered types.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  * @param mimes          Content MIME types supported.
  * @param minduration    Minimum video ad duration in seconds.
  * @param maxduration    Maximum video ad duration in seconds.
  * @param protocol       DEPRECATED Supported video bid response protocol.
  * @param protocols      Array of supported video bid response protocols.
  *                       Refer to List 5.8. At least one supported protocol must be
  *                       specified in either the protocol or protocols attribute.
  * @param w              Width of the video player in pixels.
  * @param h              Height of the video player in pixels.
  * @param startdelay     Indicates the start delay in seconds for pre-roll, mid-roll,
  *                       or post-roll ad placements. Refer to List 5.10 for additional
  *                       generic values.
  * @param linearity      Indicates if the impression must be linear, nonlinear, etc.
  *                       If none specified, assume all are allowed. Refer to List 5.7.
  * @param sequence       If multiple ad impressions are offered in the same bid request,
  *                       the sequence number will allow for the coordinated delivery
  *                       of multiple creatives
  * @param battr          Blocked creative attributes. Refer to List 5.3.
  * @param maxextended    Maximum extended video ad duration if extension is allowed.
  *                       If blank or 0, extension is not allowed. If -1, extension is
  *                       allowed, and there is no time limit imposed. If greater than 0,
  *                       then the value represents the number of seconds of extended
  *                       play supported beyond the maxduration value.
  * @param minbitrate     Minimum bit rate in Kbps. Exchange may set this dynamically
  *                       or universally across their set of publishers.
  * @param maxbitrate     Maximum bit rate in Kbps. Exchange may set this dynamically
  *                       or universally across their set of publishers.
  * @param boxingallowed  Indicates if letter-boxing of 4:3 content into a 16:9 window is
  *                       allowed, where 0 = no, 1 = yes.
  * @param playbackmethod Allowed playback methods. If none specified, assume all are
  *                       allowed. Refer to List 5.9.
  * @param delivery       Supported delivery methods (e.g., streaming, progressive). If
  *                       none specified, assume all are supported. Refer to List 5.13.
  * @param pos            Ad position on screen. Refer to List 5.4.
  * @param companionad    Array of Banner objects (Section 3.2.3) if companion ads are
  *                       available
  * @param api            List of supported API frameworks for this impression. Refer to List 5.6.
  *                       If an API is not explicitly listed, it is assumed not to be supported.
  * @param companiontype  Supported VAST companion ad types. Refer to List 5.12.
  *                       Recommended if companion Banner objects are included via
  *                       the companionad array.
  * @param ext            Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Video(
    mimes: Seq[String],
    minduration: Option[Int],
    maxduration: Option[Int],
    protocol: Option[Int],
    protocols: Option[Seq[Int]],
    w: Option[Int],
    h: Option[Int],
    startdelay: Option[Int],
    linearity: Option[Int],
    sequence: Option[Int],
    battr: Option[Seq[Int]],
    maxextended: Option[Int],
    minbitrate: Option[Int],
    maxbitrate: Option[Int],
    boxingallowed: Option[Int],
    playbackmethod: Option[Seq[Int]],
    delivery: Option[Seq[Int]],
    pos: Option[Int],
    companionad: Option[Seq[Banner]],
    api: Option[Seq[Int]],
    companiontype: Option[Seq[Int]],
    ext: Option[Any])
