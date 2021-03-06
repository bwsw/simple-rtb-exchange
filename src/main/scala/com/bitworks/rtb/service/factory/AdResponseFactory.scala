package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.{AdRequest, Imp}
import com.bitworks.rtb.model.ad.response.builder.{AdResponseBuilder, ErrorBuilder, ImpBuilder}
import com.bitworks.rtb.model.ad.response.{AdResponse, ErrorCode, Imp => ResponseImp}
import com.bitworks.rtb.model.http.ContentTypeModel
import com.bitworks.rtb.model.response.{Bid, BidResponse}
import com.bitworks.rtb.service.{Configuration, DataValidationException, Logging}

/**
  * Factory for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @author Egor Ilchenko
  */
trait AdResponseFactory {

  /**
    * Returns [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
    *
    * @param request   [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    * @param responses seqence of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    */
  def create(request: AdRequest, responses: Seq[BidResponse]): AdResponse

  /**
    * Returns [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
    *
    * @param request   [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    * @param errorCode [[com.bitworks.rtb.model.ad.response.ErrorCode.Value ErrorCode.Value]]
    */
  def create(request: AdRequest, errorCode: ErrorCode.Value): AdResponse

  /**
    * Returns [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
    *
    * @param errorCode [[com.bitworks.rtb.model.ad.response.ErrorCode.Value ErrorCode.Value]]
    * @param ct        [[com.bitworks.rtb.model.http.ContentTypeModel contentType]] of request
    */
  def create(errorCode: ErrorCode.Value, ct: ContentTypeModel): AdResponse
}

/**
  * Implementation of
  * [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]] factory.
  *
  * @author Egor Ilchenko
  */
class AdResponseFactoryImpl(configuration: Configuration) extends AdResponseFactory with Logging {
  override def create(request: AdRequest, responses: Seq[BidResponse]) = {
    val imps = responses
      .flatMap(_.seatBid)
      .head
      .bid
      .flatMap(getImp(request, _))

    if (imps.isEmpty) {
      throw new DataValidationException(ErrorCode.NO_AD_FOUND)
    }

    AdResponseBuilder(request.ct)
      .withId(request.id)
      .withImp(imps)
      .build
  }

  override def create(request: AdRequest, errorCode: ErrorCode.Value) = {
    val error = ErrorBuilder(errorCode, configuration.errorMessages).build
    AdResponseBuilder(request.ct)
      .withId(request.id)
      .withError(error)
      .build
  }

  override def create(errorCode: ErrorCode.Value, ct: ContentTypeModel) = {
    val error = ErrorBuilder(errorCode, configuration.errorMessages).build
    AdResponseBuilder(ct)
      .withError(error)
      .build
  }

  /**
    * Returns [[com.bitworks.rtb.model.ad.response.Imp Imp]].
    *
    * @param request [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    * @param bid     [[com.bitworks.rtb.model.response.Bid Bid]]
    */
  private def getImp(request: AdRequest, bid: Bid): Option[ResponseImp] = {
    val `type` = request.imp.find(_.id == bid.impId) match {
      case Some(Imp(_, Some(_), None, None)) => ImpBuilder.bannerType
      case Some(Imp(_, None, Some(_), None)) => ImpBuilder.videoType
      case Some(Imp(_, None, None, Some(_))) => ImpBuilder.nativeType
      case _ =>
        log.debug(s"cannot determine type of adRequest. r: $request; bid: $bid")
        return None
    }
    val adMarkup = bid.adm match {
      case Some(adm) if adm.nonEmpty => adm
      case _ =>
        log.debug(s"cannot build ad response. Empty adm")
        return None
    }

    Some(ImpBuilder(bid.impId, adMarkup, `type`).build)
  }
}
