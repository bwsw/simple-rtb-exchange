package com.bitworks.rtb.parser.request

import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.parser.DataValidationException

/**
  * Parser for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @author Egor Ilchenko
  */
trait AdRequestParser {
  /**
    * Returns parsed [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
    *
    * @param s input string
    * @throws com.bitworks.rtb.parser.DataValidationException in case of invalid JSON
    */
  def parse(s: String): AdRequest = {
    try{
      parseInternal(s)
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
    * @param s input string
    */
  protected def parseInternal(s: String): AdRequest
}
