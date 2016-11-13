package com.bitworks.rtb.model.message

/**
  * Unsuccessful bid request.
  *
  * @param message error message
  * @author Egor Ilchenko
  */
case class BidRequestFail(message: String) extends BidRequestResult
