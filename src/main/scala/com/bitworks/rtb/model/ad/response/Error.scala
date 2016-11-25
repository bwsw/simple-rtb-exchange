package com.bitworks.rtb.model.ad.response

/**
  * Ad response error.
  *
  * @param code    error code
  * @param message error message
  * @author Pavel Tomskikh
  */
case class Error(code: ErrorCode.Value, message: String)
