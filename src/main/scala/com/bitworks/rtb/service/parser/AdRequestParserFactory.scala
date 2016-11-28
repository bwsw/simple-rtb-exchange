package com.bitworks.rtb.service.parser

import com.bitworks.rtb.model.http.{ContentTypeModel, Json}
import com.bitworks.rtb.service.{DataValidationException, HandlerRepository}

/**
  * Factory for ad request parsers.
  *
  * @author Egor Ilchenko
  */
trait AdRequestParserFactory {

  /**
    * Returns parser for given content type.
    *
    * @param ct content type
    * @throws DataValidationException in case of missing handler
    */
  def getParser(ct: ContentTypeModel): AdRequestParser
}

/**
  * Factory implpementation for ad request parsers.
  *
  * @author Egor Ilchenko
  */
class AdRequestParserFactoryImpl(
    jsonParser: AdRequestJsonParser) extends HandlerRepository[AdRequestParser]
  with AdRequestParserFactory {

  register(Json -> jsonParser)

  override def getParser(ct: ContentTypeModel) = getHandler(ct)
}

/**
  * Factory implpementation for ad request parsers.
  *
  * @author Egor Ilchenko
  */
object AdRequestParserFactoryImpl {
  def apply() = new AdRequestParserFactoryImpl(new AdRequestJsonParser)
}
