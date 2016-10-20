package com.bitworks.rtb.model.request

/** Describes the publisher of the media in which the ad will be displayed.
  *
  * @param id     exchange-specific publisher ID
  * @param name   publisher name
  * @param cat    IAB content categories that describe the publisher
  *               See List 5.1 Content Categories in
  *               OpenRTB API Specification Version 2.3 for details.
  * @param domain highest level domain of the publisher
  * @param ext    placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/17/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
case class Publisher(
    id: Option[String],
    name: Option[String],
    cat: Option[Seq[String]],
    domain: Option[String],
    ext: Option[Any])
