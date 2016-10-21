package com.bitworks.rtb.model.response

/**
  * Bid response.
  *
  * @param id ID of the bid request to which this is a response
  * @param seatBid array of [[com.bitworks.rtb.model.response.SeatBid SeatBid]];
  *                1+ required if a bid is to be made
  * @param bidId bidder generated response ID to assist with logging/tracking
  * @param cur bid currency using ISO-4217 alpha codes
  * @param customData optional feature to allow a bidder to set data in the exchange’s cookie
  * @param nbr reason for not bidding
  * @param ext placeholder for bidder-specific extensions to OpenRTB.
  * @author Egor Ilchenko
  *
  */
case class BidResponse(
    id: String,
    seatBid: Seq[SeatBid],
    bidId: Option[String],
    cur: String,
    customData: Option[String],
    nbr: Option[Int],
    ext: Option[Any])

