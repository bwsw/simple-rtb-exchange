package com.bitworks.rtb.model.message

import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.response.BidResponse

/**
  * Send win notices to bidders.
  *
  * @author Egor Ilchenko
  */
case class SendWinNotice(request: BidRequest, responses: Seq[BidResponse])
