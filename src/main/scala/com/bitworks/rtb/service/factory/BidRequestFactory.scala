package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.request.builder.{BidRequestBuilder, ImpBuilder, VideoBuilder}

/**
  * Factory for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @author Egor Ilchenko
  */
trait BidRequestFactory {

  /**
    * Returns [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
    *
    * @param ad [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    */
  def create(ad: AdRequest): Option[BidRequest]
}

/**
  * Dummy factory implementation.
  */
class BidRequestFactoryImpl extends BidRequestFactory {
  override def create(ad: AdRequest) = {
    val imp = ImpBuilder("impID").withVideo(VideoBuilder(Seq.empty).build).build
    Some(BidRequestBuilder("bidRequestID", Seq(imp)).build)
  }
}
