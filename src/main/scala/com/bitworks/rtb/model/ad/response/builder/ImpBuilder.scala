package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.Imp

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.Imp Imp]].
  *
  * @param id   value of id in [[com.bitworks.rtb.model.ad.response.Imp Imp]]
  * @param type value of type in [[com.bitworks.rtb.model.ad.response.Imp Imp]]
  * @param adm  value of adm in [[com.bitworks.rtb.model.ad.response.Imp Imp]]
  * @author Pavel Tomskikh
  */
class ImpBuilder private(id: String, adm: String, `type`: Int) {

  def build = Imp(id, adm, `type`)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.Imp Imp]].
  *
  * @author Pavel Tomskikh
  */
object ImpBuilder {
  def apply(id: String, adm: String, `type`: Int) = new ImpBuilder(id, adm, `type`)
}
