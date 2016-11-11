package com.bitworks.rtb.service.parser

import com.bitworks.rtb.model.response.BidResponse
import com.bitworks.rtb.service.DataValidationException

/**
  * Parser for [[com.bitworks.rtb.model.response.BidResponse BidResponse]] parsers.
  *
  * @author Pavel Tomskikh
  */
trait BidResponseParser {

  /**
    * Returns parsed [[com.bitworks.rtb.model.response.BidResponse BidResponse]] object.
    *
    * @param bytes input bytes
    * @throws DataValidationException in case of invalid byte representation
    */
  def parse(bytes: Array[Byte]): BidResponse = {
    try {
      parseInternal(bytes)
    }
    catch {
      case e: DataValidationException => throw e
      case e: Throwable => throw new DataValidationException(cause = e)
    }
  }

  /**
    * Returns parsed  [[com.bitworks.rtb.model.response.BidResponse BidResponse]] object without
    * exception catching.
    *
    * @param bytes input bytes
    */
  protected def parseInternal(bytes: Array[Byte]): BidResponse
}
