package com.bitworks.rtb.service.factory

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
    * @param bid [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    */
  def create(bid: BidResponse): AdResponse

  /**
    * Returns [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
    *
    * @param error [[com.bitworks.rtb.model.ad.response.Error Error]]
    */
  def create(error: Error): AdResponse
}

/**
  * Dummy factory implementation.
  */
class AdResponseFactoryImpl extends AdResponseFactory {
  override def create(bid: BidResponse): AdResponse = {
    AdResponseBuilder("someid")
      .withError(
        Error(
          1,
          "ad response factory not implemented"))
      .build
  }

  override def create(error: Error): AdResponse = {
    AdResponseBuilder("someid")
      .withError(error)
      .build
  }
}
