package com.bitworks.rtb.service

import com.bitworks.rtb.model.http.{ContentType, HttpHeaderModel}

/**
  * Repository for handlers associated with HTTP headers and content type.
  *
  * @author Egor Ilchenko
  */
trait HandlerRepository[H] {
  private var handlersByHeaders = Map[HttpHeaderModel, H]()

  /**
    * Registers handlers in repository.    *
    */
  protected def register(regs: (ContentType, H)*) =
  regs.foreach { case (ct, handler) =>
    handlersByHeaders = handlersByHeaders + (ct.header -> handler)
  }

  /**
    * Returns handler for given content type.
    *
    * @param ct content type
    * @throws DataValidationException in case of missing handler
    */
  protected def getHandler(ct: ContentType) = {
    handlersByHeaders.get(ct.header) match {
      case Some(handler) => handler
      case None =>
        val str = handlersByHeaders.map(x => s"${x._1.name}: ${x._1.value}")
        throw new DataValidationException(
          s"cannot find handler for $ct. Known " +
            s"content types: $str")
    }
  }

  /**
    * Returns handler for given headers.
    *
    * @param headers HTTP request headers
    * @throws DataValidationException in case of missing handler
    */
  protected def getHandler(headers: Seq[HttpHeaderModel]) = {
    handlersByHeaders.collectFirst {
      case (header, value) if headers.contains(header) => value
    } match {
      case Some(handler) => handler
      case None =>
        val str = handlersByHeaders.map(x => s"${x._1.name}: ${x._1.value}")
        throw new DataValidationException(
          s"cannot find handler for $headers. Known " +
            s"headers: $str")
    }
  }
}
