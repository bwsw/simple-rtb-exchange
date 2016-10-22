package com.bitworks.rtb.model.request

/**
  * The top-level bid request object that contains a globally unique bid request or auction ID.
  *
  * @param id      unique ID of the bid request, provided by the exchange
  * @param imp     array of [[com.bitworks.rtb.model.request.Imp Imp]] objects representing the
  *                impressions offered
  * @param site    details via a [[com.bitworks.rtb.model.request.Site Site]] object about the
  *                publisher’s website
  * @param app     details via an [[com.bitworks.rtb.model.request.App App]] object about the
  *                publisher’s app (i.e., non-browser applications)
  * @param device  details via a [[com.bitworks.rtb.model.request.Device Device]] object about the
  *                user’s device to which the impression will be delivered
  * @param user    details via a [[com.bitworks.rtb.model.request.User User]] object about the
  *                human user of the device; the advertising audience
  * @param test    indicator of test mode in which auctions are not billable, where 0 = live
  *                mode, 1 = test mode
  * @param at      auction type, where 1 = First Price, 2 = Second Price Plus, greater than 500 =
  *                exchange-specific auction types
  * @param tmax    maximum time in milliseconds to submit a bid to avoid timeout
  * @param wseat   whitelist of buyer seats allowed to bid on this impression
  * @param allImps flag to indicate if Exchange can verify that the impressions offered represent
  *                all of the impressions available in context (e.g., all on the web page, all
  *                video spots such as pre/mid/post roll) to support road-blocking. 0 = no or
  *                unknown, 1 = yes, the impressions offered represent all that are available.
  * @param cur     array of allowed currencies for bids on this bid request using ISO-4217 alpha
  *                codes
  * @param bcat    blocked advertiser categories using the IAB content categories
  * @param badv    bock list of advertisers by their domains
  * @param regs    a [[com.bitworks.rtb.model.request.Regs Regs]] object that specifies any
  *                industry, legal, or governmental regulations in force for this request
  * @param ext     placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskih
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
