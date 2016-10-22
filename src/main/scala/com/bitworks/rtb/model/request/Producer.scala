package com.bitworks.rtb.model.request

/**
  * A producer of the content in which the ad will be shown.
  *
  * @param id     content producer or originator ID
  * @param name   content producer or originator name
  * @param cat    IAB content categories that describe the content producer
  * @param domain highest level domain of the content producer
  * @param ext    placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskih
  */
case class Producer(
    id: Option[String],
    name: Option[String],
    cat: Option[Seq[String]],
    domain: Option[String],
    ext: Option[Any])
