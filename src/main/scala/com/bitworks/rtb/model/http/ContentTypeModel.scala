package com.bitworks.rtb.model.http

/**
  * HTTP Content-Type.
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
  * Avro content type.
  *
  * @author Egor Ilchenko
  */
case object Avro extends ContentTypeModel

/**
  * Protobuf content type.
  *
  * @author Egor Ilchenko
  */
case object Protobuf extends ContentTypeModel

/**
  * Unknown content type.
  *
  * @author Egor Ilchenko
  */
case object Unknown extends ContentTypeModel
