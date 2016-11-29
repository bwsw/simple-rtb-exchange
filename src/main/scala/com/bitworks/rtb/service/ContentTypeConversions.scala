package com.bitworks.rtb.service

import scala.language.implicitConversions
import akka.http.scaladsl.model.{ContentType, ContentTypes, MediaType}
import com.bitworks.rtb.model.http._

/**
  * Implicit conversions for [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]].
  *
  * @author Egor Ilchenko
  */
object ContentTypeConversions {
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
