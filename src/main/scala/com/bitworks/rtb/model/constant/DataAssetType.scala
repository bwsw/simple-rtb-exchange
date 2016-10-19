package com.bitworks.rtb.model.constant

/** List of common asset element types of native advertising.
  * See List 7.3 in OpenRTB Native Ads API Specification Version 1.0.0.2 for details.
  */
object DataAssetType {
  sealed abstract class DataAssetType(i: Int) extends Enum(i)

  case object Sponsored extends DataAssetType(1)
  case object Desc extends DataAssetType(2)
  case object Rating extends DataAssetType(3)
  case object Likes extends DataAssetType(4)
  case object Downloads extends DataAssetType(5)
  case object Price extends DataAssetType(6)
  case object SalePrice extends DataAssetType(7)
  case object Phone extends DataAssetType(8)
  case object Address extends DataAssetType(9)
  case object Desc2 extends DataAssetType(10)
  case object DisplayUrl extends DataAssetType(11)
  case object CtaText extends DataAssetType(12)
  case class Custom(i: Int) extends DataAssetType(i) {
    require(i >= 500)
  }

  def apply(id: Int): DataAssetType = id match {
    case 1 => Sponsored
    case 2 => Desc
    case 3 => Rating
    case 4 => Likes
    case 5 => Downloads
    case 6 => Price
    case 7 => SalePrice
    case 8 => Phone
    case 9 => Address
    case 10 => Desc2
    case 11 => DisplayUrl
    case 12 => CtaText
    case i: Int => Custom(i)
  }
}
