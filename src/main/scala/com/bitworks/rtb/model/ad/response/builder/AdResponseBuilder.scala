package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.{AdResponse, Error, Imp}

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @param id value of id in [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]] object
  * @author Pavel Tomskikh
  */
class AdResponseBuilder private(id: String) {
  var imp: Option[Seq[Imp]] = None
  var error: Option[Error] = None

  def withImp(s: Seq[Imp]) = {
    imp = Some(s)
    this
  }

  def withError(e: Error) = {
    error = Some(e)
    this
  }

  def build = AdResponse(id, imp, error)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @author Pavel Tomskikh
  */
object AdResponseBuilder {
  def apply(id: String) = new AdResponseBuilder(id)
}
