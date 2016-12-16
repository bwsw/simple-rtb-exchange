package com.bitworks.rtb.model.request

/**
  * A private marketplace container for direct deals between buyers and sellers.
  *
  * @param privateAuction indicator of auction eligibility to seats named in the Direct Deals
  *                       object, where 0 = all bids are accepted, 1 = bids are restricted to
  *                       the deals specified and the terms thereof
  * @param deals          array of [[com.bitworks.rtb.model.request.Deal Deal]] objects that convey
  *                       the specific deals applicable to this impression
  * @param ext            placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Pmp(
    privateAuction: Option[Int],
    deals: Option[Seq[Deal]],
    ext: Option[Any])
