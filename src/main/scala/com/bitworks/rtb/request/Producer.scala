package com.bitworks.rtb.request

/** Defines the producer of the content in which the ad will be shown.
  *
  * @param id     content producer or originator ID
  * @param name   content producer or originator name
  * @param cat    IAB content categories that describe the content producer
  *               See List 5.1 Content Categories in
  *               OpenRTB API Specification Version 2.3 for details.
  * @param domain highest level domain of the content producer
  * @param ext    placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/17/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
case class Producer(
    id: Option[String],
    name: Option[String],
    cat: Option[Seq[String]],
    domain: Option[String],
    ext: Option[Any])
