package com.bitworks.rtb.model.response

/** Collection of bids made by the bidder on behalf of a specific seat.
  *
  * A bid response can contain multiple SeatBid objects, each on behalf of a different bidder seat and each
  * containing one or more individual bids. If multiple impressions are presented in the request, the group
  * attribute can be used to specify if a seat is willing to accept any impressions that it can win (default) or if
  * it is only interested in winning any if it can win them all as a group.
  *
  * @param bid Array of 1+ Bid objects (Section 4.2.3) each related to an
  *            impression. Multiple bids can relate to the same impression.
  * @param seat ID of the bidder seat on whose behalf this bid is made
  * @param group 0 = impressions can be won individually; 1 = impressions must
  *              be won or lost as a group.
  * @param ext Placeholder for bidder-specific extensions to OpenRTB.
  */
case class SeatBid(bid: Seq[Bid], seat: Option[String], group: Int, ext: Option[Any])



