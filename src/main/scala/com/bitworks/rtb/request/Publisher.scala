package com.bitworks.rtb.request

/** Describes the publisher of the media in which the ad will be displayed.
  *
  * @param id exchange-specific publisher ID
  * @param name publisher name
  * @param cat IAB content categories that describe the publisher
  * @param domain highest level domain of the publisher
  */
class Publisher(
                 val id: String,
                 val name: String,
                 val cat: Array[String],
                 val domain: String
               ) {

}
