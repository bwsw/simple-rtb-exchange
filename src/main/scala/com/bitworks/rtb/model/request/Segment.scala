package com.bitworks.rtb.model.request

/**
  * Specific units of data about the user.
  *
  * @param id    ID of the data segment specific to the data provider
  * @param name  name of the data segment specific to the data provider
  * @param value string representation of the data segment value
  * @param ext   placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Segment(
    id: Option[String],
    name: Option[String],
    value: Option[String],
    ext: Option[Any])
