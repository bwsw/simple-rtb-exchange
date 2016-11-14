package com.bitworks.rtb.service.validator

import com.bitworks.rtb.model.request.{App, BidRequest, Imp}
import com.bitworks.rtb.model.response._

/**
  * Validator for [[com.bitworks.rtb.model.response.BidResponse BidResponse]] according to
  * [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object.
  *
  * @author Pavel Tomskikh
  */
class BidResponseValidator {

  /**
    * Returns Some([[com.bitworks.rtb.model.response.BidResponse BidResponse]]) if bidResponse is
    * valid, or None else.
    *
    * @param bidRequest  [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object
    * @param bidResponse validated [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    *                    object
    */
  def validate(bidRequest: BidRequest, bidResponse: BidResponse): Option[BidResponse] = {
    if (bidResponse.id == bidRequest.id &&
      bidResponse.bidId.forall(_.nonEmpty) &&
      bidResponse.cur.nonEmpty &&
      bidResponse.customData.forall(_.nonEmpty)) {

      var seatBids: Seq[SeatBid] = bidResponse.seatBid

      if (bidResponse.nbr.isEmpty) {
        seatBids = bidResponse.seatBid.map(validateSeatBid(bidRequest)).filter(_.nonEmpty)
          .map(_.get)
        if (seatBids.isEmpty) return None
      }

      Some(
        BidResponse(
          bidResponse.id,
          seatBids,
          bidResponse.bidId,
          bidResponse.cur,
          bidResponse.customData,
          bidResponse.nbr,
          bidResponse.ext))
    }

    else None
  }

  private def validateSeatBid(bidRequest: BidRequest)(seatBid: SeatBid): Option[SeatBid] = {
    seatBid.group match {
      case 0 =>
        val bids = seatBid.bid.filter(validateBid(bidRequest, seatBid.seat))
        if (bids.nonEmpty) Some(SeatBid(bids, seatBid.seat, seatBid.group, seatBid.ext))
        else None
      case 1 =>
        if (seatBid.bid.forall(validateBid(bidRequest, seatBid.seat))) Some(seatBid)
        else None
      case _ => None
    }
  }

  private def validateBid(bidRequest: BidRequest, seat: Option[String])(bid: Bid): Boolean = {
    bidRequest.imp.find(_.id == bid.impId) match {
      case Some(imp) =>
        bid.id.nonEmpty && (
          bid.dealId match {
            case Some(dealId) => validateBidWithDeal(bidRequest, imp, seat, bid)
            case None => validateBidWithoutDeal(bidRequest, imp, seat, bid)
          }) &&
          bid.adId.forall(_.nonEmpty) &&
          bid.nurl.forall(_.nonEmpty) &&
          bid.adm.forall(_.nonEmpty) &&
          bidRequest.app.forall(checkBundle(bid.bundle)) &&
          bid.iurl.forall(_.nonEmpty) &&
          bid.cid.forall(_.nonEmpty) &&
          bid.crid.forall(_.nonEmpty) &&
          isAllNotInBlackList(bid.cat, bidRequest.bcat) &&
          checkSize(bid.h, bid.w, imp)
      case None => false
    }
  }

  private def checkSize(h: Option[Int], w: Option[Int], imp: Imp): Boolean = {
    if (imp.banner.nonEmpty) {
      val banner = imp.banner.get
      checkOneDimension(h, banner.h, banner.hmin, banner.hmax) &&
        checkOneDimension(w, banner.w, banner.wmin, banner.wmax)
    }
    else if (imp.video.nonEmpty) {
      val video = imp.video.get
      (video.h.isEmpty || video.h == h) &&
        (video.w.isEmpty || video.w == w)
    }
    else true
  }

  private def checkOneDimension(
      dimension: Option[Int],
      expected: Option[Int],
      min: Option[Int],
      max: Option[Int]): Boolean = {
    if (dimension.nonEmpty) {
      expected.nonEmpty && dimension.get == expected.get ||
        (min.isEmpty || dimension.get >= min.get) &&
          (max.isEmpty || dimension.get <= max.get)
    }
    else {
      expected.isEmpty &&
        max.isEmpty &&
        min.isEmpty
    }
  }

  private def checkBundle(bundle: Option[String])(app: App): Boolean = {
    app.bundle.forall { b =>
      bundle.nonEmpty &&
        b == bundle.get
    }
  }

  private def validateBidWithDeal(
      bidRequest: BidRequest,
      imp: Imp,
      seat: Option[String],
      bid: Bid): Boolean = {
    imp.pmp.nonEmpty &&
      imp.pmp.get.deals.nonEmpty && (
      imp.pmp.get.deals.get.find(_.id == bid.dealId.get) match {
        case Some(deal) =>
          bid.price >= deal.bidFloor && (
            bid.adomain.isEmpty ||
              isLeastOneInWhiteList(bid.adomain, deal.wadomain) ||
              isLeastOneNotInBlackList(bid.adomain, bidRequest.badv)) && (
            deal.wseat.isEmpty ||
              seat.nonEmpty &&
                deal.wseat.get.contains(seat.get))
        case None => false
      })
  }

  private def validateBidWithoutDeal(
      bidRequest: BidRequest,
      imp: Imp,
      seat: Option[String],
      bid: Bid): Boolean = {
    bid.price >= imp.bidFloor && (
      bid.adomain.isEmpty ||
        isLeastOneNotInBlackList(bid.adomain, bidRequest.badv))
  }

  private def isLeastOneInWhiteList[T](
      items: Option[Seq[T]],
      whiteList: Option[Seq[T]]): Boolean = {
    whiteList.isEmpty ||
      items.nonEmpty &&
        whiteList.get.intersect(items.get).nonEmpty
  }

  private def isLeastOneNotInBlackList[T](
      items: Option[Seq[T]],
      blackList: Option[Seq[T]]): Boolean = {
    blackList.isEmpty ||
      items.nonEmpty &&
        items.get.exists(!blackList.get.contains(_))
  }

  private def isAllNotInBlackList[T](items: Option[Seq[T]], blackList: Option[Seq[T]]): Boolean = {
    blackList.isEmpty ||
      items.isEmpty ||
      !items.get.exists(blackList.get.contains)
  }

}
