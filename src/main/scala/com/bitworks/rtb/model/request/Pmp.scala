package com.bitworks.rtb.model.request

/**
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * @param private_auction Indicator of auction eligibility to seats named in the Direct Deals
  *                        object, where 0 = all bids are accepted, 1 = bids are restricted to
  *                        the deals specified and the terms thereof.
  * @param deals           Array of Deal (Section 3.2.18) objects that convey the specific deals
  *                        applicable
  *                        to this impression.
  * @param ext             Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Pmp(
    private_auction: Option[Int],
    deals: Option[Seq[Deal]],
    ext: Option[Any])
