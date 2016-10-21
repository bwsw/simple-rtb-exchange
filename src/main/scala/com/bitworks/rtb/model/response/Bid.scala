package com.bitworks.rtb.model.response


/**
  * Offer to buy a specific impression under certain business terms.
  *
  * @param id bidder generated bid ID to assist with logging/tracking
  * @param impid ID of the Imp object in the related bid request
  * @param price bid price expressed as CPM although the actual transaction
  *              is for a unit impression only
  * @param adid ID of a preloaded ad to be served if the bid wins
  * @param nurl win notice URL called by the exchange if the bid wins
  * @param adm optional means of conveying ad markup in case the bid wins
  * @param adomain advertiser domain for block list checking
  * @param bundle bundle or package name of the app being advertised,
  *               if applicable; intended to be a unique ID across exchanges
  * @param iurl URL without cache-busting to an image that is representative
  *             of the content of the campaign for ad quality/safety checking
  * @param cid campaign ID to assist with ad quality checking; the collection
  *            of creatives for which iurl should be representative
  * @param crid creative ID to assist with ad quality checking
  * @param cat IAB content categories of the creative
  * @param attr set of attributes describing the creative
  * @param dealId reference to the deal.id from the bid request if this bid pertains
  *               to a private marketplace direct deal
  * @param h height of the creative in pixels
  * @param w width of the creative in pixels
  * @param ext placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  *
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
    dealId: Option[String],
    h: Option[Int],
    w: Option[Int],
    ext: Option[Any])

