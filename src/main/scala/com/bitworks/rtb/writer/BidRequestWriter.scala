package com.bitworks.rtb.writer

import com.bitworks.rtb.model.request.BidRequest

/**
  * Writer for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @author Pavel Tomskikh
  */
trait BidRequestWriter {

  /**
    * Returns generated string.
    *
    * @param b a [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object
    */
  def write(b: BidRequest): String

}
