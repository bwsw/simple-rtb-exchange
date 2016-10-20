package com.bitworks.rtb.model.request

/** Native type impression.
  *
  * Native ad units are intended to blend seamlessly into the surrounding content (e.g., a
  * sponsored Twitter or Facebook post). As such, the response must be well-structured to afford
  * the publisher fine-grained control over rendering.
  *
  * The Native Subcommittee has developed a companion specification to OpenRTB called the Native
  * Ad Specification. It defines the request parameters and response markup structure of native
  * ad units. This object provides the means of transporting request parameters as an opaque
  * string so that the specific parameters can evolve separately under the auspices of the Native
  * Ad Specification. Similarly, the ad markup served will be structured according to that
  * specification.
  *
  * The presence of a Native as a subordinate of the Imp object indicates that this impression
  * is offered as a native type impression. At the publisher’s discretion, that same impression
  * may also be offered as banner and/or video by also including as Imp subordinates the Banner
  * and/or Video objects, respectively. However, any given bid for the impression must conform to
  * one of the offered types.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * @param request Request payload complying with the Native Ad Specification.
  * @param ver     Version of the Native Ad Specification to which request complies; highly
  *                recommended for efficient parsing.
  * @param api     List of supported API frameworks for this impression. Refer to List 5.6. If an
  *                API
  *                is not explicitly listed, it is assumed not to be supported.
  * @param battr   Blocked creative attributes. Refer to List 5.3.
  * @param ext     Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Native(
    request: String,
    ver: Option[String],
    api: Option[Seq[Int]],
    battr: Option[Seq[Int]],
    ext: Option[Any])
