package com.bitworks.rtb.model.ad.response

/**
  * Error codes.
  *
  * @author Pavel Tomskikh
  */
object ErrorCode extends Enumeration {
  val UNKNOWN_ERROR = Value(0)
  val MISSING_HEADER = Value(1)
  val INCORRECT_HEADER_VALUE = Value(2)
  val INCORRECT_REQUEST = Value(3)
  val SITE_OR_APP_NOT_FOUND = Value(4)
  val SITE_OR_APP_INACTIVE = Value(5)
  val PUBLISHER_NOT_FOUND = Value(6)
  val IAB_CATEGORY_NOT_FOUND = Value(7)
  val NO_AD_FOUND = Value(8)
}
