package com.bitworks.rtb.model.request

/**
  * Additional data about the user.
  *
  * @param id      exchange-specific ID for the data provider
  * @param name    exchange-specific name for the data provider
  * @param segment [[com.bitworks.rtb.model.request.Segment Segment]] objects that contain the
  *                actual data values
  * @param ext     placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Data(
    id: Option[String],
    name: Option[String],
    segment: Option[Seq[Segment]],
    ext: Option[Any])
