package com.bitworks.rtb.model.response

import com.bitworks.rtb.model.constant._

/** Bid response top level object
 *
  * @param id ID of the bid request to which this is a response.
  * @param seatbid Array of seatbid objects; 1+ required if a bid is to be made.
  * @param bidid Bidder generated response ID to assist with logging/tracking.
  * @param cur Bid currency using ISO-4217 alpha codes.
  * @param customData Optional feature to allow a bidder to set data in the
  *                   exchange’s cookie. The string must be in base85 cookie safe
  *                   characters and be in any format. Proper JSON encoding must
  *                   be used to include “escaped” quotation marks.
  * @param nbr Reason for not bidding. Refer to List 5.19.
  * @param ext Placeholder for bidder-specific extensions to OpenRTB.
  */
case class BidResponse(
    id: String,
    seatbid: Seq[SeatBid],
    bidid: Option[String],
    cur: String,
    customData: Option[String],
    nbr: Option[NoBidReason.Value],
    ext: Option[Any])

