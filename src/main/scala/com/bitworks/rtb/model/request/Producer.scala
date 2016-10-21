package com.bitworks.rtb.model.request

/**
  * Information about producer of the content in which the ad will be shown.
  *
  * @param id     content producer or originator ID
  * @param name   content producer or originator name
  * @param cat    IAB content categories that describe the content producer
  * @param domain highest level domain of the content producer
  * @param ext    placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/17/2016
  *
  * @author Pavel Tomskikh
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
