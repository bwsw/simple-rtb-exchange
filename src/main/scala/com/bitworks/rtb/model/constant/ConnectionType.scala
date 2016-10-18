package com.bitworks.rtb.model.constant

/** The various options for the type of device connectivity.
  *
  * See List 5.18 in documentation
  *
  */
object ConnectionType extends Enumeration{
  val unknown = Value(0)
  val ethernet = Value(1)
  val wifi = Value(2)
  val celluralUnknown = Value(3)
  val cellural2g = Value(4)
  val cellural3g = Value(5)
  val cellural4g = Value(6)
}
