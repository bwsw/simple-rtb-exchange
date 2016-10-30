package com.bitworks.rtb.parser

import com.bitworks.rtb.model.ad.request.AdRequest

/**
  * Parser for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @author Egor Ilchenko
  */
trait Parser {
  /**
    * Returns parsed [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
    *
    * @param bytes input bytes
    * @throws DataValidationException in case of invalid byte representation
    */
  def parseAdRequest(bytes: Array[Byte]): AdRequest = {
    try {
      parseAdRequestInternal(bytes)
    }
    catch {
      case e: DataValidationException => throw e
      case e: Throwable => throw new DataValidationException(cause = e)
    }
  }

  /**
    * Returns parsed [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] without
    * exception catching.
    *
    * @param bytes input bytes
    */
  protected def parseAdRequestInternal(bytes: Array[Byte]): AdRequest
}
