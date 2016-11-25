package com.bitworks.rtb.service.parser

import com.bitworks.rtb.model.http.{HttpHeaderModel, JSON}
import com.bitworks.rtb.service.{DataValidationException, HandlerRepository}

/**
  * Factory for bid response parsers.
  *
  * @author Egor Ilchenko
  */
trait BidResponseParserFactory {
  /**
    * Returns parser for given headers.
    *
    * @param headers HTTP headers
    * @throws DataValidationException in case of missing handler
    */
  def getParser(headers: Seq[HttpHeaderModel]): BidResponseParser
}

/**
  * Factory implementation for bid response parsers.
  *
  * @author Egor Ilchenko
  */
class BidResponseParserFactoryImpl(
    jsonParser: BidResponseJsonParser) extends HandlerRepository[BidResponseParser]
  with BidResponseParserFactory {

  register(JSON -> jsonParser)

  override def getParser(headers: Seq[HttpHeaderModel]) = getHandler(headers)
}

/**
  * Factory implementation for bid response parsers.
  *
  * @author Egor Ilchenko
  */
object BidResponseParserFactoryImpl {
  def apply() = new BidResponseParserFactoryImpl(new BidResponseJsonParser)
}
