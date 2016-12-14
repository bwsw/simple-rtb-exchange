package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.Error
import com.bitworks.rtb.model.ad.response.ErrorCode._
import com.typesafe.config.Config

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.Error Error]].
  *
  * @param code          error code
  * @param errorMessages configuration with error messages
  * @author Pavel Tomskikh
  */
class ErrorBuilder private(code: Value, errorMessages: Config) {

  def build = Error(code.id, getMessage(code.toString))

  private def getMessage(code: String) = {
    if (errorMessages.hasPath(code)) {
      errorMessages.getString(code)
    } else {
      ""
    }
  }
}

object ErrorBuilder {
  def apply(code: Value, errorMessages: Config): ErrorBuilder =
    new ErrorBuilder(code, errorMessages)
}
