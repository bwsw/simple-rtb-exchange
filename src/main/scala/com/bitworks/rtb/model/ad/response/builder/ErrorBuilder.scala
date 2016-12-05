package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.Error
import com.bitworks.rtb.model.ad.response.ErrorCode._
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.Error Error]].
  *
  * @author Pavel Tomskikh
  */
class ErrorBuilder private(code: Value, conf: Config) {

  def build = Error(code, getMessage(code.toString))

  private def getMessage(code: String) = {
    if (conf.hasPath(code)) {
      conf.getString(code)
    } else {
      ""
    }
  }
}

object ErrorBuilder {
  private val conf = ConfigFactory.parseResources("error_messages.properties")

  def apply(code: Value): ErrorBuilder = new ErrorBuilder(code, conf)
}
