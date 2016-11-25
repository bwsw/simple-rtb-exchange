package com.bitworks.rtb.service

import com.bitworks.rtb.model.http.ContentTypeModel

/**
  * Repository for handlers associated with content type.
  *
  * @author Egor Ilchenko
  */
trait HandlerRepository[H] {
  private var handlersByContentType = Map[ContentTypeModel, H]()

  /**
    * Registers handlers in repository.    *
    */
  protected def register(regs: (ContentTypeModel, H)*) =
  regs.foreach { case (ct, handler) =>
    handlersByContentType = handlersByContentType + (ct -> handler)
  }

  /**
    * Returns handler for given content type.
    *
    * @param ct content type
    * @throws DataValidationException in case of missing handler
    */
  protected def getHandler(ct: ContentTypeModel) = {
    handlersByContentType.get(ct) match {
      case Some(handler) => handler
      case None =>
        val str = handlersByContentType.keys
        throw new DataValidationException(
          s"cannot find handler for $ct. Known " +
            s"content types: $str")
    }
  }
}
