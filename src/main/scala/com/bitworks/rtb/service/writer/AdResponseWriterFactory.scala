package com.bitworks.rtb.service.writer

import com.bitworks.rtb.model.http.{ContentTypeModel, Json}
import com.bitworks.rtb.service.HandlerRepository

/**
  * Factory for ad response writers.
  *
  * @author Egor Ilchenko
  */
trait AdResponseWriterFactory {

  /**
    * Returns ad response writer for given content type.
    *
    * @param ct content type
    */
  def getWriter(ct: ContentTypeModel): AdResponseWriter
}

/**
  * Factory implementation for ad response writers.
  *
  * @author Egor Ilchenko
  */
class AdResponseWriterFactoryImpl(
    jsonWriter: AdResponseJsonWriter) extends HandlerRepository[AdResponseWriter]
  with AdResponseWriterFactory {

  register(Json -> jsonWriter)

  override def getWriter(ct: ContentTypeModel) = getHandler(ct)
}

/**
  * Factory implementation for ad response writers.
  *
  * @author Egor Ilchenko
  */
object AdResponseWriterFactoryImpl {
  def apply() = new AdResponseWriterFactoryImpl(new AdResponseJsonWriter)
}
