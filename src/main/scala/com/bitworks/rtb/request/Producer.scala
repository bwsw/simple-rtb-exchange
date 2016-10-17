package com.bitworks.rtb.request

/** Defines the producer of the content in which the ad will be shown.
  *
  * @param id content producer or originator ID
  * @param name content producer or originator name
  * @param cat IAB content categories that describe the content producer
  * @param domain highest level domain of the content producer
  */
class Producer(
                val id: String,
                val name: String,
                val cat: Array[String],
                val domain: String
              ) {

}
