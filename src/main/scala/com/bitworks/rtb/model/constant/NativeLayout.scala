package com.bitworks.rtb.model.constant

/** List of the core layouts.
  * See List 7.1 in OpenRTB Native Ads API Specification Version 1.0.0.2 for details
  */
object NativeLayout {
  sealed abstract class NativeLayout(i: Int) extends Enum(i)

  case object ContentWall extends NativeLayout(1)
  case object AppWall extends NativeLayout(2)
  case object NewsFeed extends NativeLayout(3)
  case object ChatList extends NativeLayout(4)
  case object Carousel extends NativeLayout(5)
  case object ContentStream extends NativeLayout(6)
  case object GridAdjoining extends NativeLayout(7)
  case class Custom(i: Int) extends NativeLayout(i) {
    require(i >= 500)
  }

  def apply(id: Int): NativeLayout = id match {
    case 1 => ContentWall
    case 2 => AppWall
    case 3 => NewsFeed
    case 4 => ChatList
    case 5 => Carousel
    case 6 => ContentStream
    case 7 => GridAdjoining
    case i: Int => Custom(i)
  }
}
