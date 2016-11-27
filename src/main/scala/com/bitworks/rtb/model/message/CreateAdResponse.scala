package com.bitworks.rtb.model.message

import com.bitworks.rtb.model.response.BidResponse

/**
  * Create ad response message.
  *
  * @param responses bid responses with bids containing ad markup.
  * @author Egor Ilchenko
  */
case class CreateAdResponse(responses: Seq[BidResponse])
