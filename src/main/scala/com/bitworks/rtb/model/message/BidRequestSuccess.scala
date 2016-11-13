package com.bitworks.rtb.model.message

import com.bitworks.rtb.model.response.BidResponse

/**
  * Successful bid request.
  *
  * @param response [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
  * @author Egor Ilchenko
  */
case class BidRequestSuccess(response: BidResponse) extends BidRequestResult
