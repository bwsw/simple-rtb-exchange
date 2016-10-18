package com.bitworks.rtb.model.constant

/** List of common asset element types of native advertising */
// Not complete
object DataAssetType {

  sealed abstract class DataAssetType(id: Int)

  case object Sponsored extends DataAssetType(1)

}
