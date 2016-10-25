package com.bitworks.rtb.model.ad.response

/**
  * The top-level ad response object.
  *
  * @param id    ID of the ad response
  * @param imp   details about ad impression
  * @param error details about error
  * @author Pavel Tomskikh
  */
case class AdResponse(
    id: String,
    imp: Option[AdResponseImp],
    error: Option[Error])
