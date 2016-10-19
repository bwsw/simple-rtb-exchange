package com.bitworks.rtb.model.constant

/** List of common image asset element types of native advertising.
  * See List 7.4 in OpenRTB Native Ads API Specification Version 1.0.0.2 for details.
  */
object ImageAssetType {
  sealed abstract class ImageAssetType(i: Int) extends Enum(i)

  case object Icon extends ImageAssetType(1)
  case object Logo extends ImageAssetType(2)
  case object Main extends ImageAssetType(3)
  case class Custom(i: Int) extends ImageAssetType(i) {
    require(i >= 500)
  }

  def apply(id: Int): ImageAssetType = id match {
    case 1 => Icon
    case 2 => Logo
    case 3 => Main
    case i: Int => Custom(i)
  }
}
