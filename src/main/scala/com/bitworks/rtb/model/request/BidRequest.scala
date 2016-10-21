package com.bitworks.rtb.model.request

/**
  * Information about bid request.
  *
  * @param id      unique ID of the bid request
  * @param imp     impressions offered
  * @param site    details about the publisher’s website.
  * @param app     details about the publisher’s app
  * @param device  details about the user’s device
  * @param user    details about the human user of the device
  * @param test    indicates of test mode in which auctions are not billable
  * @param at      auction type, where 1 = First Price, 2 = Second Price Plus
  * @param tmax    maximum time in milliseconds to submit a bid to avoid timeout
  * @param wseat   whitelist of buyer seats allowed to bid on this impression
  * @param allImps indicates if exchange can verify that the impressions offered represent all of
  *                the impressions available in context to support road-blocking
  * @param cur     allowed currencies for bids on this bid request using ISO-4217 alpha codes
  * @param bcat    blocked advertiser categories using the IAB content categories
  * @param badv    bock list of advertisers by their domains
  * @param regs    specifies any industry, legal, or governmental regulations
  * @param ext     placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/20/2016
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
case class BidRequest(
    id: String,
    imp: Seq[Imp],
    site: Option[Site],
    app: Option[App],
    device: Option[Device],
    user: Option[User],
    test: Option[Int],
    at: Option[Int],
    tmax: Option[Int],
    wseat: Option[Seq[String]],
    allImps: Option[Int],
    cur: Option[Seq[String]],
    bcat: Option[Seq[String]],
    badv: Option[Seq[String]],
    regs: Option[Regs],
    ext: Option[Any])
