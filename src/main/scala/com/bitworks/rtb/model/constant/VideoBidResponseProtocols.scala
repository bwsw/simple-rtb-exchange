package com.bitworks.rtb.model.constant

/** Lists the options for video bid response protocols that could be supported
  *  by an exchange.
  */
object VideoBidResponseProtocols extends Enumeration {
  val Vast1 = Value(1)
  val Vast2 = Value(2)
  val Vast3 = Value(3)
  val Vast1Wrapper = Value(4)
  val Vast2Wrapper = Value(5)
  val Vast3Wrapper = Value(6)
}
