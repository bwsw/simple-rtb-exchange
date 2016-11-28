package com.bitworks.rtb.service.parser

import com.bitworks.rtb.model.http.{ContentTypeModel, Json, Unknown}
import com.bitworks.rtb.service.{DataValidationException, HandlerRepository}

/**
  * Factory for bid response parsers.
  *
  * @author Egor Ilchenko
  */
trait BidResponseParserFactory {
  /**
    * Returns parser for given content type.
    *
    * @param ct content type
    * @throws DataValidationException in case of missing handler
    */
  def getParser(ct: ContentTypeModel): BidResponseParser
}

/**
  * Factory implementation for bid response parsers.
  *
  * @author Egor Ilchenko
  */
class BidResponseParserFactoryImpl(
    jsonParser: BidResponseJsonParser) extends HandlerRepository[BidResponseParser]
  with BidResponseParserFactory {

  register(Json -> jsonParser)

  override def getParser(ct: ContentTypeModel) = getHandler(ct)
}

/**
  * Factory implementation for bid response parsers.
  *
  * @author Egor Ilchenko
  */
object BidResponseParserFactoryImpl {
  def apply() = new BidResponseParserFactoryImpl(new BidResponseJsonParser)
}
