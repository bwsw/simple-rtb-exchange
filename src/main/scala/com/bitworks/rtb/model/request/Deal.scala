package com.bitworks.rtb.model.request

/** Specific deal that was struck a priori between a buyer and a seller.
  * Its presence with the Pmp collection indicates that this impression is available
  * under the terms of that deal. Refer to Section 7.2 for more details.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * @param id          A unique identifier for the direct deal.
  * @param bidfloor    Minimum bid for this impression expressed in CPM.
  * @param bidfloorcur Currency specified using ISO-4217 alpha codes.
  *                    This may be different from bid currency returned
  *                    by bidder if this is allowed by the exchange.
  * @param at          Optional override of the overall auction type of the bid request,
  *                    where 1  = First  Price, 2 = Second Price Plus, 3 = the value passed
  *                    in bidfloor is the agreed upon deal price. Additional auction
  *                    types can be defined by the exchange.
  * @param wseat       Whitelist of buyer seats allowed to bid on this deal. Seat IDs must be
  *                    communicated between bidders and the exchange a priori. Omission implies
  *                    no seat
  *                    restrictions.
  * @param wadomain    Array of advertiser domains (e.g., advertiser.com) allowed to bid on this
  *                    deal. Omission implies no advertiser restrictions.
  * @param ext         Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Deal(
    id: String,
    bidfloor: BigDecimal,
    bidfloorcur: String,
    at: Option[Int],
    wseat: Option[Seq[String]],
    wadomain: Option[Seq[String]],
    ext: Option[Any])
