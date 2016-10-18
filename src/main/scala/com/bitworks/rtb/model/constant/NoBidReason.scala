package com.bitworks.rtb.model.constant

/** The options for a bidder to signal the exchange as to why
  * it did not offer a bid for the impression.
  *
  * See List 5.19 in documentation
  *
  */
object NoBidReason extends Enumeration{
  val unknownError = Value(0)
  val technicalError = Value(1)
  val invalidRequest = Value(2)
  val knownWebSpider = Value(3)
  val suspectedNonHumanTraffic = Value(4)
  val cloudOrProxyIP = Value(5)
  val unsupportedDevice = Value(6)
  val blockedPublisherOrSite = Value(7)
  val unmatchedUser = Value(8)
}
