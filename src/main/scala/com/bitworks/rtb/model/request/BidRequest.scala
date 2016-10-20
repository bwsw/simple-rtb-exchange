package com.bitworks.rtb.model.request

/** The top-level bid request object contains a globally unique bid request or auction ID. This
  * id attribute is required as is at least one impression object (Section 3.2.2). Other
  * attributes in this top-level object establish rules and restrictions that apply to all
  * impressions being offered.
  *
  * There are also several subordinate objects that provide detailed data to potential buyers.
  * Among these are the Site and App objects, which describe the type of published media in which
  * the impression(s) appear. These objects are highly recommended, but only one applies to a
  * given bid request depending on whether the media is browser-based web content or a
  * non-browser application, respectively.
  *
  * @param id      unique ID of the bid request, provided by the exchange
  * @param imp     array of Imp objects representing the impressions offered. At least 1 Imp
  *                object is required.
  * @param site    details via a Site object about the publisher’s website.
  * @param app     details via an App object (Section 3.2.7) about the publisher’s app (i.e.,
  *                non-browser applications)
  * @param device  details via a Device object (Section 3.2.11) about the user’s device to which
  *                the impression will be delivered
  * @param user    details via a User object (Section 3.2.13) about the human user of the device;
  *                the advertising audience
  * @param test    indicator of test mode in which auctions are not billable, where 0 = live
  *                mode, 1 = test mode
  * @param at      auction type, where 1 = First Price, 2 = Second Price Plus. Exchange-specific
  *                auction types can be defined using values greater than 500.
  * @param tMax    maximum time in milliseconds to submit a bid to avoid timeout. This value is
  *                commonly communicated offline.
  * @param wSeat   whitelist of buyer seats allowed to bid on this impression. Seat IDs must be
  *                communicated between bidders and the exchange a priori. Omission implies no
  *                seat restrictions.
  * @param allImps flag to indicate if Exchange can verify that the impressions offered represent
  *                all of the impressions available in context (e.g., all on the web page, all
  *                video spots such as pre/mid/post roll) to support road-blocking. 0 = no or
  *                unknown, 1 = yes, the impressions offered represent all that are available.
  * @param cur     array of allowed currencies for bids on this bid request using ISO-4217 alpha
  *                codes. Recommended only if the exchange accepts multiple currencies.
  * @param bCat    blocked advertiser categories using the IAB content categories. See List 5.1
  *                Content Categories in OpenRTB API Specification Version 2.3 for details.
  * @param badv    bock list of advertisers by their domains
  * @param regs    a Regs object that specifies any industry, legal, or governmental regulations
  *                in force for this request
  * @param ext     placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/20/2016
  * @author Tomskih Pavel
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
    tMax: Option[Int],
    wSeat: Option[Seq[String]],
    allImps: Option[Int],
    cur: Option[Seq[String]],
    bCat: Option[Seq[String]],
    badv: Option[Seq[String]],
    regs: Option[Regs],
    ext: Option[Any])
