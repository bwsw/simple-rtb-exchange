package com.bitworks.rtb.service.parser

import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.ad.response.ErrorCode
import com.bitworks.rtb.service.DataValidationException

/**
  * Parser for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @author Egor Ilchenko
  */
trait AdRequestParser {
  /**
    * Returns parsed [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
    *
    * @param bytes input bytes
    * @throws DataValidationException in case of invalid byte representation
    */
  def parse(bytes: Array[Byte]): AdRequest = {
    try {
      parseInternal(bytes)
    }
    catch {
      case e: DataValidationException => throw e
      case e: Throwable =>
        throw new DataValidationException(ErrorCode.INCORRECT_REQUEST, e)
    }
  }

  /**
    * Returns parsed [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] without
    * exception catching.
    *
    * @param bytes input bytes
    */
  protected def parseInternal(bytes: Array[Byte]): AdRequest
}
