package com.bitworks.rtb.model.constant

/** List of the core ad unit ids.
  * See List 7.2 in OpenRTB Native Ads API Specification Version 1.0.0.2 for details
  */
object NativeAdUnit {
  sealed abstract class NativeAdUnit(i: Int) extends Enum(i)

  case object PaidSearchUnit extends NativeAdUnit(1)
  case object RecommendationWidget extends NativeAdUnit(2)
  case object PromotedListings extends NativeAdUnit(3)
  case object InAd extends NativeAdUnit(4)
  case class Custom(i: Int) extends NativeAdUnit(i) {
    require(i >= 500)
  }

  def apply(id: Int): NativeAdUnit = id match {
    case 1 => PaidSearchUnit
    case 2 => RecommendationWidget
    case 3 => PromotedListings
    case 4 => InAd
    case i: Int => Custom(i)
  }
}
