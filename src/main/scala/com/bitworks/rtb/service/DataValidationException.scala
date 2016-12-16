package com.bitworks.rtb.service

import com.bitworks.rtb.model.ad.response.ErrorCode

/**
  * An exception that indicates about incorrectness of data.
  *
  * @author Pavel Tomskikh
  */
class DataValidationException(
    errorCode: ErrorCode.Value,
    cause: Throwable = null)
  extends Exception(
    DataValidationException.defaultMessage(errorCode),
    cause) {
  def getError = errorCode
}

object DataValidationException {
  def defaultMessage(errorCode: ErrorCode.Value): String = errorCode.toString
}
