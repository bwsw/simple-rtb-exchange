package com.bitworks.rtb.service

import akka.http.scaladsl.model.{ContentType, ContentTypes}
import com.bitworks.rtb.model.http.{ContentTypeModel, Json, Unknown}

/**
  * Implicit conversions between content type models.
  *
  * @author Egor Ilchenko
  */
object ContentTypeConversions {

  implicit def toAkka(ct: ContentTypeModel): ContentType = {
    ct match {
      case Json => ContentTypes.`application/json`
      case Unknown => ContentTypes.NoContentType
    }
  }

  implicit def fromAkka(ct: ContentType): ContentTypeModel = {
    ct match {
      case ContentTypes.`application/json` => Json
      case _ => Unknown
    }
  }
}
