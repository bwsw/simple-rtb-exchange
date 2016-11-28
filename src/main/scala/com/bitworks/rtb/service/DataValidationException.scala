package com.bitworks.rtb.service

import com.bitworks.rtb.model.ad.response.Error

/**
  * An exception that indicates about incorrectness of data.
  *
  * @author Pavel Tomskikh
  */
class DataValidationException(
    error: Error,
    cause: Throwable = null)
  extends Exception(
    DataValidationException.defaultMessage(error),
    cause) {
  def getError = error
}

object DataValidationException {
  def defaultMessage(error: Error): String = error.toString
}
