package com.bitworks.rtb.model.response

/**
  * Collection of bids made by the bidder on behalf of a specific seat.
  *
  * @param bid array of 1+ [[com.bitworks.rtb.model.response.Bid Bid]] each related to an impression
  * @param seat ID of the bidder seat on whose behalf this bid is made
  * @param group 0 = impressions can be won individually; 1 = impressions must
  *              be won or lost as a group
  * @param ext placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  *
  */
case class SeatBid(bid: Seq[Bid], seat: Option[String], group: Int, ext: Option[Any])



