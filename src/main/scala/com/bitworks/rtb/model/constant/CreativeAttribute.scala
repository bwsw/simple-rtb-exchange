package com.bitworks.rtb.model.constant

/** Creative attributes that can describe an ad being served or
  * serve as restrictions of thereof.
  *
  * See List 5.3 in documentation
  */
object CreativeAttribute extends Enumeration{
  val audioAdAutoPlay = Value(1)
  val audioAdUserInitiated = Value(2)
  val expandableAutomatic = Value(3)
  val expandableUserClick = Value(4)
  val expandableUserRollover = Value(5)
  val inBannerVideoAdAutoPlay = Value(6)
  val inBannerVideoAdUserInitiated = Value(7)
  val pop = Value(8)
  val provocative = Value(9)
  val shaky = Value(10)
  val surveys = Value(11)
  val textOnly = Value(12)
  val userInteractive = Value(13)
  val windowsDialog = Value(14)
  val hasAudioOnOffButton = Value(15)
  val adCanBeSkipped = Value(16)
}
