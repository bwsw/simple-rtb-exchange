package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.Error
import com.bitworks.rtb.model.ad.response.ErrorCode._
import com.bitworks.rtb.service.Configuration
import com.typesafe.config.Config
import scaldi.Injectable.inject
import scaldi.Injector

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.Error Error]].
  *
  * @author Pavel Tomskikh
  */
class ErrorBuilder private(code: Value, conf: Config) {

  def build = Error(code.id, getMessage(code.toString))

  private def getMessage(code: String) = {
    if (conf.hasPath(code)) {
      conf.getString(code)
    } else {
      ""
    }
  }
}

object ErrorBuilder {
  def apply(code: Value)(implicit injector: Injector): ErrorBuilder =
    new ErrorBuilder(code, inject[Configuration].errorMessages)
}
