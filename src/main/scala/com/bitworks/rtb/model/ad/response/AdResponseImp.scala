package com.bitworks.rtb.model.ad.response

/**
  * An ad response impression.
  *
  * @param id unique ID of the impression
  * @param adm ad markup
  * @param type impression type
  * @author Pavel Tomskikh
  */
case class AdResponseImp(
    id: String,
    adm: String,
    `type`: Int)
