package com.bitworks.rtb.model.request

/** Information known or derived about the human user of the device.
  *
  * The user id is an exchange artifact and may be subject to rotation or other
  * privacy policies. However, this user ID must be stable long enough to serve
  * reasonably as the basis for frequency capping and retargeting.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * @param id Exchange-specific ID for the user. At least one of id or buyerid is recommended.
  * @param buyerid Buyer-specific ID for the user as mapped by the exchange for the buyer.
  * @param yob Year of birth as a 4-digit integer.
  * @param gender Gender, where “M” = male, “F” = female, “O” = known to be
  *               other (i.e., omitted is unknown).
  * @param keywords Comma separated list of keywords, interests, or intent.
  * @param customdata Optional feature to pass bidder data that was set in the
  *                   exchange’s cookie. The string must be in base85 cookie safe
  *                   characters and be in any format.
  * @param geo Location of the user’s home base. This is not necessarily their current location.
  * @param data Additional user data. Each Data object represents a different data source.
  * @param ext Placeholder for exchange-specific extensions to OpenRTB.
  */
case class User(
    id: Option[String],
    buyerid: Option[String],
    yob: Option[Int],
    gender: Option[String],
    keywords: Option[String],
    customdata: Option[String],
    geo: Option[Geo],
    data: Option[Seq[Data]],
    ext: Option[Any])