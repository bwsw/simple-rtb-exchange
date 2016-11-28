package com.bitworks.rtb.service.writer

import com.bitworks.rtb.model.http.{ContentTypeModel, Json}
import com.bitworks.rtb.service.HandlerRepository

/**
  * Factory for bid request writers.
  *
  * @author Egor Ilchenko
  */
trait BidRequestWriterFactory {

  /**
    * Returns bid request writer for given content type.
    *
    * @param ct content type
    */
  def getWriter(ct: ContentTypeModel): BidRequestWriter
}

/**
  * Factory implemetnation for bid request writers.
  *
  * @author Egor Ilchenko
  */
class BidRequestWriterFactoryImpl(
    jsonWriter: BidRequestJsonWriter) extends HandlerRepository[BidRequestWriter]
  with BidRequestWriterFactory {

  register(Json -> jsonWriter)

  override def getWriter(ct: ContentTypeModel) = getHandler(ct)
}

/**
  * Factory implemetnation for bid request writers.
  *
  * @author Egor Ilchenko
  */
object BidRequestWriterFactoryImpl {
  def apply() = new BidRequestWriterFactoryImpl(new BidRequestJsonWriter)
}
