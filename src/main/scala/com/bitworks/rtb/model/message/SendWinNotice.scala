package com.bitworks.rtb.model.message

import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.response.BidResponse

/**
  *
  *
  * @author Egor Ilchenko
  */
case class SendWinNotice(request: BidRequest, response: BidResponse)
