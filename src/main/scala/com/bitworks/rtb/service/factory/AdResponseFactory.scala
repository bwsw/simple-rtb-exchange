package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.{AdRequest, Imp}
import com.bitworks.rtb.model.ad.response.{AdResponse, Error}
import com.bitworks.rtb.model.ad.response.builder.{AdResponseBuilder, ImpBuilder}
import com.bitworks.rtb.model.response.{Bid, BidResponse}
import com.bitworks.rtb.service.{DataValidationException, Logging}

/**
  * Factory for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @author Egor Ilchenko
  */
trait AdResponseFactory {

  /**
    * Returns [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
    *
    * @param request [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    * @param bid     [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    */
  def create(request: AdRequest, bid: BidResponse): AdResponse

  /**
    * Returns [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
    *
    * @param request [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    * @param error   [[com.bitworks.rtb.model.ad.response.Error Error]]
    */
  def create(request: AdRequest, error: Error): AdResponse
}

/**
  * Implementation of
  * [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]] factory.
  *
  * @author Egor Ilchenko
  */
class AdResponseFactoryImpl extends AdResponseFactory with Logging {
  override def create(request: AdRequest, bid: BidResponse) = {
    val imps = bid
      .seatBid
      .head
      .bid
      .map(getImp(request, _))

    AdResponseBuilder(request.id)
      .withImp(imps)
      .build
  }

  override def create(request: AdRequest, error: Error) = {
    AdResponseBuilder(request.id)
      .withError(error)
      .build
  }

  /**
    * Returns [[com.bitworks.rtb.model.ad.response.Imp Imp]].
    *
    * @param request [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    * @param bid     [[com.bitworks.rtb.model.response.Bid Bid]]
    */
  private def getImp(request: AdRequest, bid: Bid) = {
    val `type` = request.imp.find(_.id == bid.impId) match {
      case Some(Imp(_, Some(_), None, None)) => 1
      case Some(Imp(_, None, Some(_), None)) => 2
      case Some(Imp(_, None, None, Some(_))) => 3
      case _ =>
        log.error(s"cannot determine type of adRequest. r: $request; bid: $bid")
        throw new DataValidationException("cannot determine ad response type")
    }
    val adMarkup = bid.adm match {
      case Some(adm) => adm
      case None =>
        log.error(s"cannot build ad response. Empty adm")
        throw new DataValidationException("cannot build ad response. Empty adm")
    }

    ImpBuilder(bid.impId, adMarkup, `type`).build
  }
}
