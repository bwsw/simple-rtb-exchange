package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.{AdResponse, Error, Imp}
import com.bitworks.rtb.model.http.ContentTypeModel

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @author Pavel Tomskikh
  */
class AdResponseBuilder private( ct: ContentTypeModel) {
  var id: Option[String] = None
  var imp: Option[Seq[Imp]] = None
  var error: Option[Error] = None

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withImp(s: Seq[Imp]) = {
    imp = Some(s)
    this
  }

  def withError(e: Error) = {
    error = Some(e)
    this
  }

  def build = AdResponse(id, imp, error, ct)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @author Pavel Tomskikh
  */
object AdResponseBuilder {
  def apply(ct: ContentTypeModel) = new AdResponseBuilder(ct)
}
