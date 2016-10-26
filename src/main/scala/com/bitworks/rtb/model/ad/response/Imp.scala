package com.bitworks.rtb.model.ad.response

/**
  * An ad response impression.
  *
  * @param id unique ID of the impression
  * @param adm ad markup
  * @param type ad type where 1 = banner, 2 = video, 3 = native
  * @author Pavel Tomskikh
  */
case class Imp(
    id: String,
    adm: String,
    `type`: Int)
