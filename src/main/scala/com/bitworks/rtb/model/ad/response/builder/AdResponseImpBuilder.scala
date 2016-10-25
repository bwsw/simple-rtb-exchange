package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.AdResponseImp

/**
  * Builder for [[com.bitworks.rtb.model.ad.response.AdResponseImp AdResponseImp]].
  *
  * @param id   value of id in [[com.bitworks.rtb.model.ad.response.AdResponseImp AdResponseImp]]
  * @param type value of type in [[com.bitworks.rtb.model.ad.response.AdResponseImp AdResponseImp]]
  * @param adm  value of adm in [[com.bitworks.rtb.model.ad.response.AdResponseImp AdResponseImp]]
  * @author Pavel Tomskikh
  */
class AdResponseImpBuilder private(id: String, adm: String, `type`: Int) {

  def build = AdResponseImp(id, adm, `type`)
}

object AdResponseImpBuilder {
  def apply(id: String, adm: String, `type`: Int) = new AdResponseImpBuilder(id, adm, `type`)
}
