package com.bitworks.rtb.model.http

import akka.http.scaladsl.model.{ContentType, ContentTypes, MediaType}

import scala.language.implicitConversions

/**
  * Value of HTTP Content-Type header.
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

object ContentTypeModel {
  private val `avro/binary` = ContentType(
    MediaType.customBinary("avro", "binary", MediaType.NotCompressible))

  private val `application/x-protobuf` = ContentType(
    MediaType.customBinary("application", "x-protobuf", MediaType.NotCompressible))

  /**
    * Converts [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    * to akka http content type.
    *
    * @param ct [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    * @return akka http content type
    */
  implicit def toAkka(ct: ContentTypeModel): ContentType = {
    ct match {
      case Json => ContentTypes.`application/json`
      case Avro => `avro/binary`
      case Protobuf => `application/x-protobuf`
      case Unknown => ContentTypes.NoContentType
    }
  }

  /**
    * Converts akka http content type to
    * [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]].
    *
    * @param ct akka http content type
    * @return [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    */
  implicit def fromAkka(ct: ContentType): ContentTypeModel = {
    ct match {
      case ContentTypes.`application/json` => Json
      case `avro/binary` => Avro
      case `application/x-protobuf` => Protobuf
      case _ => Unknown
    }
  }
}
