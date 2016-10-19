package com.bitworks.rtb.request

/** Defines the producer of the content in which the ad will be shown.
  *
  * @param id content producer or originator ID
  * @param name content producer or originator name
  * @param cat IAB content categories that describe the content producer
  * @param domain highest level domain of the content producer
  */
class Producer(
    id: Option[String],
    name: Option[String],
    cat: Option[Array[String]],
    domain: Option[String])
