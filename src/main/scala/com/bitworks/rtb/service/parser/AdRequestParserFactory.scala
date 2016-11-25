package com.bitworks.rtb.service.parser

import com.bitworks.rtb.model.http.{HttpHeaderModel, JSON}
import com.bitworks.rtb.service.{DataValidationException, HandlerRepository}

/**
  * Factory for ad request parsers.
  *
  * @author Egor Ilchenko
  */
trait AdRequestParserFactory {

  /**
    * Returns parser for given headers.
    *
    * @param headers HTTP headers
    * @throws DataValidationException in case of missing handler
    */
  def getParser(headers: Seq[HttpHeaderModel]): AdRequestParser
}

/**
  * Factory implpementation for ad request parsers.
  *
  * @author Egor Ilchenko
  */
class AdRequestParserFactoryImpl(
    jsonParser: AdRequestJsonParser) extends HandlerRepository[AdRequestParser]
  with AdRequestParserFactory {

  register(JSON -> jsonParser)

  override def getParser(headers: Seq[HttpHeaderModel]) = getHandler(headers)
}

/**
  * Factory implpementation for ad request parsers.
  *
  * @author Egor Ilchenko
  */
object AdRequestParserFactoryImpl {
  def apply() = new AdRequestParserFactoryImpl(new AdRequestJsonParser)
}
