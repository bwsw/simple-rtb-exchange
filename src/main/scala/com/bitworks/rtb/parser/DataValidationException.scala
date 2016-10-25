package com.bitworks.rtb.parser

/**
  * Can be thrown when validate fails.
  *
  * @author Pavel Tomskikh
  */

class DataValidationException(
    message: String = null,
    cause: Throwable = null)
    extends Exception(
      DataValidationException.defaultMessage(message, cause),
      cause)

object DataValidationException {
  def defaultMessage(message: String, cause: Throwable): String = {
    if (message != null) message
    else if (cause != null) cause.toString
    else null
  }
}
