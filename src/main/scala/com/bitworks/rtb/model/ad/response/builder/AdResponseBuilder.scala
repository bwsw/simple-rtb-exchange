package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.{AdResponse, AdResponseImp, Error}

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @param id value of id in [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]] object
  * @author Pavel Tomskikh
  */
class AdResponseBuilder private(id: String) {
  var imp: Option[AdResponseImp] = None
  var error: Option[Error] = None

  def withImp(a: AdResponseImp) = {
    imp = Some(a)
    this
  }

  def withError(e: Error) = {
    error = Some(e)
    this
  }

  def build = AdResponse(id, imp, error)
}

object AdResponseBuilder {
  def apply(id: String) = new AdResponseBuilder(id)
}
