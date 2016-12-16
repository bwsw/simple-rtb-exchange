package com.bitworks.rtb.model.request

/**
  * A specific deal that was struck a priori between a buyer and a seller.
  *
  * @param id          a unique identifier for the direct deal
  * @param bidFloor    minimum bid for this impression expressed in CPM
  * @param bidFloorCur currency specified using ISO-4217 alpha codes
  * @param at          optional override of the overall auction type of the bid request,
  *                    where 1 = First  Price, 2 = Second Price Plus, 3 = the value passed
  *                    in bidfloor is the agreed upon deal price. Additional auction
  *                    types can be defined by the exchange.
  * @param wseat       whitelist of buyer seats allowed to bid on this deal
  * @param wadomain    array of advertiser domains (e.g., advertiser.com) allowed to bid on this
  *                    deal
  * @param ext         placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Deal(
    id: String,
    bidFloor: BigDecimal,
    bidFloorCur: String,
    at: Option[Int],
    wseat: Option[Seq[String]],
    wadomain: Option[Seq[String]],
    ext: Option[Any])
