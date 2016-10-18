package com.bitworks.rtb.model.constant

/** The options to indicate how the geographic information was determined.
  *
  * See List 5.16 in documentation
  *
  */
object LocationType extends Enumeration {
  val gpsLocationService = Value(1)
  val ipAddress = Value(2)
  val userProvided = Value(3)
}
