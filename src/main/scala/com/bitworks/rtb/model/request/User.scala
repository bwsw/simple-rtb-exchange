package com.bitworks.rtb.model.request

/**
  * Information known or derived about the human user of the device.
  *
  * @param id         exchange-specific ID for the user
  * @param buyerId    buyer-specific ID for the user as mapped by the exchange for the buyer
  * @param yob        year of birth as a 4-digit integer
  * @param gender     gender, where “M” = male, “F” = female, “O” = known to be
  *                   other (i.e., omitted is unknown)
  * @param keywords   comma separated list of keywords, interests, or intent
  * @param customData optional feature to pass bidder data that was set in the
  *                   exchange’s cookie. The string must be in base85 cookie safe
  *                   characters and be in any format.
  * @param geo        location of the user’s home base; see
  *                   [[com.bitworks.rtb.model.request.Geo Geo]]
  * @param data       additional user data. Each [[com.bitworks.rtb.model.request.Data Data]] object
  *                   represents a different data source
  * @param ext        placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class User(
    id: Option[String],
    buyerId: Option[String],
    yob: Option[Int],
    gender: Option[String],
    keywords: Option[String],
    customData: Option[String],
    geo: Option[Geo],
    data: Option[Seq[Data]],
    ext: Option[Any])