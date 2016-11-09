package com.bitworks.rtb.validator

import com.bitworks.rtb.model.response._

/**
  * Validator for [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
  *
  * @author Pavel Tomskikh
  */
class BidResponseValidator {

  /**
    * Returns true if [[com.bitworks.rtb.model.response.BidResponse BidResponse]] object is valid.
    *
    * @param bidResponse [[com.bitworks.rtb.model.response.BidResponse BidResponse]] object
    */
  def validate(bidResponse: BidResponse): Boolean = {
    bidResponse.id.nonEmpty &&
      (bidResponse.seatBid.nonEmpty &&
        bidResponse.seatBid.forall(validateSeatBid) ||
        bidResponse.nbr.nonEmpty) &&
      bidResponse.bidId.forall(_.nonEmpty) &&
      bidResponse.customData.forall(_.nonEmpty)
  }

  /**
    * Returns true if [[com.bitworks.rtb.model.response.SeatBid SeatBid]] object is valid.
    *
    * @param seatBid [[com.bitworks.rtb.model.response.SeatBid SeatBid]] object
    */
  private def validateSeatBid(seatBid: SeatBid): Boolean = {
    seatBid.bid.nonEmpty &&
      seatBid.bid.forall(validateBid) &&
      Seq(0, 1).contains(seatBid.group)
  }

  /**
    * Returns true if [[com.bitworks.rtb.model.response.Bid Bid]] object is valid.
    *
    * @param bid [[com.bitworks.rtb.model.response.Bid Bid]] object
    */
  private def validateBid(bid: Bid): Boolean = {
    bid.id.nonEmpty &&
      bid.impId.nonEmpty &&
      bid.price > 0 &&
      bid.adId.forall(_.nonEmpty) &&
      bid.nurl.forall(_.nonEmpty) &&
      bid.adm.forall(_.nonEmpty) &&
      bid.adomain.forall(_.forall(_.nonEmpty)) &&
      bid.bundle.forall(_.nonEmpty) &&
      bid.iurl.forall(_.nonEmpty) &&
      bid.cid.forall(_.nonEmpty) &&
      bid.crid.forall(_.nonEmpty) &&
      bid.cat.forall(_.forall(_.nonEmpty)) &&
      bid.dealId.forall(_.nonEmpty) &&
      bid.h.forall(_ > 0) &&
      bid.w.forall(_ > 0)
  }
}
