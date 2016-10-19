package com.bitworks.rtb.model.response


/** An offer to buy a specific impression under certain business terms.
  *
  * A SeatBid object contains one or more Bid objects, each of which relates to a specific impression in the
  * bid request via the impid attribute and constitutes an offer to buy that impression for a given price.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * @param id Bidder generated bid ID to assist with logging/tracking.
  * @param impid ID of the Imp object in the related bid request.
  * @param price Bid price expressed as CPM although the actual transaction
  *              is for a unit impression only.
  * @param adid ID of a preloaded ad to be served if the bid wins.
  * @param nurl Win notice URL called by the exchange if the bid wins.
  * @param adm Optional means of conveying ad markup in case the bid wins.
  * @param adomain Advertiser domain for block list checking.
  * @param bundle Bundle or package name (e.g., com.foo.mygame) of the app being advertised,
  *               if applicable; intended to be a unique ID across exchanges.
  * @param iurl URL without cache-busting to an image that is representative
  *             of the content of the campaign for ad quality/safety checking
  * @param cid Campaign ID to assist with ad quality checking; the collection
  *            of creatives for which iurl should be representative.
  * @param crid Creative ID to assist with ad quality checking.
  * @param cat IAB content categories of the creative. Refer to List 5.1.
  * @param attr Set of attributes describing the creative. Refer to List 5.3.
  * @param dealid Reference to the deal.id from the bid request if this bid pertains
  *               to a private marketplace direct deal.
  * @param h Height of the creative in pixels.
  * @param w Width of the creative in pixels.
  * @param ext Placeholder for bidder-specific extensions to OpenRTB.
  */
case class Bid(
    id: String,
    impid: String,
    price: BigDecimal,
    adid: Option[String],
    nurl: Option[String],
    adm: Option[String],
    adomain: Option[Seq[String]],
    bundle: Option[String],
    iurl: Option[String],
    cid: Option[String],
    crid: Option[String],
    cat: Option[Seq[String]],
    attr: Option[Set[Int]],
    dealid: Option[String],
    h: Option[Int],
    w: Option[Int],
    ext: Option[Any])

