package com.bitworks.rtb.model.ad.response.builder

import java.util.Properties

import com.bitworks.rtb.model.ad.response.Error
import com.bitworks.rtb.model.ad.response.ErrorCode._

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.Error Error]].
  *
  * @author Pavel Tomskikh
  */
class ErrorBuilder private(code: Value, properties: Properties) {
  def build = Error(code, properties.getProperty(code.toString))
}

object ErrorBuilder {
  private val propertiesFileName = "error_messages.properties"
  private val propertiesFile = getClass.getResourceAsStream(s"/$propertiesFileName")
  private val properties = new Properties()
  properties.load(propertiesFile)

  def apply(code: Value): ErrorBuilder = new ErrorBuilder(code, properties)
}
