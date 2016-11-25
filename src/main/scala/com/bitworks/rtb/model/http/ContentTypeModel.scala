package com.bitworks.rtb.model.http

/**
  * HTTP request content type.
  *
  * @author Egor Ilchenko
  */
trait ContentTypeModel

/**
  * JSON content type.
  *
  * @author Egor Ilchenko
  */
case object Json extends ContentTypeModel

/**
  * Unknown content type.
  *
  * @author Egor Ilchenko
  */
case object Unknown extends ContentTypeModel
