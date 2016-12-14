package com.bitworks.rtb.service.factory

/**
  * Enumerated lists from the specification.
  *
  * @author Pavel Tomskikh
  */
trait BidRequestConstants {
  val bannerAdTypes = 1 to 4
  val creativeAttributes = 1 to 16
  val adPosition = 0 to 7
  val expandableDirection = 1 to 5
  val apiFrameworks = 1 to 5
  val videoLinearity = 1 to 2
  val videoBidResponseProtocol = 1 to 6
  val videoPlaybackMethods = 1 to 4
  val videoQuality = 0 to 3
  val vastCompanionTypes = 1 to 3
  val contentDeliveryMethods = 1 to 2
  val contentContext = 1 to 7
  val qagMediaRatings = 1 to 3
  val locationType = 1 to 3
  val deviceType = 1 to 7
  val connectionType = 0 to 6
  val noBidReasonCodes = 0 to 8
  val genders = Seq("M", "F", "O")

  def isVideoStartDelay(i: Int) = i >= -2

  def check(range: Range)(i: Iterable[Int]): Boolean =
    i.nonEmpty && i.forall(range.contains)
}
