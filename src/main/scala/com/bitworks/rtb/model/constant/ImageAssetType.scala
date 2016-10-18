package com.bitworks.rtb.model.constant

/** List of common image asset element types of native advertising */
// Not complete
object ImageAssetType {

  sealed abstract class ImageAssetType(id: Int)

  case object Icon extends ImageAssetType(1)
  case object Logo extends ImageAssetType(2)
  case object Main extends ImageAssetType(3)


}
