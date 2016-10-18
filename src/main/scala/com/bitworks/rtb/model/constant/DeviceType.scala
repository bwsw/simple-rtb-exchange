package com.bitworks.rtb.model.constant

/** The type of device from which the impression originated.
  *
  * This OpenRTB table has values derived from the IAB Quality Assurance Guidelines (QAG).
  * Practitioners should keep in sync with updates to the QAG values as published on IAB.net.
  *
  * See List 5.17 in documentation
  *
  */
object DeviceType extends Enumeration{
  val mobileOrTablet = Value(1)
  val pc = Value(2)
  val tv = Value(3)
  val phone = Value(4)
  val tablet = Value(5)
  val connectedDevice = Value(6)
  val setTopBox = Value(7)
}
