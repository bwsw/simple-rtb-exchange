package com.bitworks.rtb.parser

import com.bitworks.rtb.model.response.BidResponse

/**
  * Interface for [[com.bitworks.rtb.model.response.BidResponse BidResponse]] parsers.
  *
  * @author Pavel Tomskikh
  */
trait BidResponseParser {

  /**
    * Returns parsed string to [[com.bitworks.rtb.model.response.BidResponse BidResponse]] object.
    *
    * @param s parsing string
    * @throws DataValidationException in case of invalid JSON
    */
  def parse(s: String): BidResponse = {
    try {
      parseInternal(s)
    }
    catch {
      case e: DataValidationException => throw e
      case e: Throwable => throw new DataValidationException(cause = e)
    }
  }

  /**
    * Returns parsed string to [[com.bitworks.rtb.model.response.BidResponse BidResponse]] object
    * without exception catching.
    *
    * @param s parsing string
    */
  protected def parseInternal(s: String): BidResponse
}
