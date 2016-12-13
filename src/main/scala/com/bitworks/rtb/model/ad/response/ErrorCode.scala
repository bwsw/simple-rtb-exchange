package com.bitworks.rtb.model.ad.response

/**
  * Error codes.
  *
  * @author Pavel Tomskikh
  */
object ErrorCode extends Enumeration {
  val UNKNOWN_ERROR = Value(0, "UNKNOWN_ERROR")
  val MISSED_HEADER = Value(1, "MISSED_HEADER")
  val INCORRECT_HEADER_VALUE = Value(2, "INCORRECT_HEADER_VALUE")
  val INCORRECT_REQUEST = Value(3, "INCORRECT_REQUEST")
  val SITE_OR_APP_NOT_FOUND = Value(4, "SITE_OR_APP_NOT_FOUND")
  val SITE_OR_APP_INACTIVE = Value(5, "SITE_OR_APP_INACTIVE")
  val PUBLISHER_NOT_FOUND = Value(6, "PUBLISHER_NOT_FOUND")
  val IAB_CATEGORY_NOT_FOUND = Value(7, "IAB_CATEGORY_NOT_FOUND")
  val NO_AD_FOUND = Value(8, "NO_AD_FOUND")
}
