package com.bitworks.rtb.model.ad.request

import com.bitworks.rtb.model.request.Geo

/**
  * Human user of the device; audience for advertising.
  *
  * @param id       exchange-specific id
  * @param yob      year of birth
  * @param gender   gender
  * @param geo      location of the home base via [[com.bitworks.rtb.model.request.Geo Geo]] object
  * @param keywords comma separated list of keywords
  * @author Egor Ilchenko
  */
case class User(
    id: Option[String],
    yob: Option[Int],
    gender: Option[String],
    keywords: Option[String],
    geo: Option[Geo])
