package com.bitworks.rtb.model.request

/**
  * A native type impression.
  *
  * @param request request payload complying with the Native Ad Specification
  * @param ver     version of the Native Ad Specification to which request complies
  * @param api     list of supported API frameworks for this impression
  * @param battr   blocked creative attributes
  * @param ext     placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Native(
    request: String,
    ver: Option[String],
    api: Option[Seq[Int]],
    battr: Option[Seq[Int]],
    ext: Option[Any])
