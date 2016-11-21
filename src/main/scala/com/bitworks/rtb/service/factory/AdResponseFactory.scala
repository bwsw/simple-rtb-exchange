package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.ad.response.{AdResponse, Error}
import com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder
import com.bitworks.rtb.model.response.BidResponse

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
  * Dummy factory implementation.
  */
class AdResponseFactoryImpl extends AdResponseFactory {
  override def create(request: AdRequest, bid: BidResponse): AdResponse = {
    AdResponseBuilder(request.id)
      .withError(
        Error(
          1,
          "ad response factory not implemented"))
      .build
  }

  override def create(request: AdRequest, error: Error): AdResponse = {
    AdResponseBuilder(request.id)
      .withError(error)
      .build
  }
}
