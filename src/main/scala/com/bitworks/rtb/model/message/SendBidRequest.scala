package com.bitworks.rtb.model.message

import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.request.BidRequest

/**
  * Send bid request to bidder.
  *
  * @param bidder  [[com.bitworks.rtb.model.db.Bidder Bidder]]
  * @param request [[com.bitworks.rtb.model.request.BidRequest BidRequest]]
  * @author Egor Ilchenko
  */
case class SendBidRequest(bidder: Bidder, request: BidRequest)
