package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.ad.response.AdResponse
import com.bitworks.rtb.model.http.ContentTypeModel
import com.bitworks.rtb.service.DataValidationException
import com.bitworks.rtb.service.parser.AdRequestParser
import com.bitworks.rtb.service.writer.AdResponseWriter

/**
  * A converter for ad models.
  *
  * @author Egor Ilchenko
  */
trait AdModelConverter {

  /**
    * Parses binary representation of [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    * in the specified format.
    *
    * @param bytes input bytes
    * @param ct    [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    * @throws DataValidationException in case of missing handler or invalid bytes
    */
  def parse(bytes: Array[Byte], ct: ContentTypeModel): AdRequest

  /**
    * Generates byte representation of [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]]
    * in the specified format.
    *
    * @param response [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]]
    */
  def write(response: AdResponse): Array[Byte]
}

/**
  * An implementation of a converter for ad models.
  *
  * @author Egor Ilchenko
  */
class AdModelConverterImpl(
    adRequestParsers: Map[ContentTypeModel, AdRequestParser],
    adResponseWriters: Map[ContentTypeModel, AdResponseWriter]) extends AdModelConverter {

  /**
    * Parses binary representation of [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]]
    * in the specified format.
    *
    * @param bytes input bytes
    * @param ct    [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    * @throws DataValidationException in case of missing handler or invalid bytes
    */
  override def parse(bytes: Array[Byte], ct: ContentTypeModel) = {
    adRequestParsers.get(ct) match {
      case Some(parser) => parser.parse(bytes)
      case None => throw new DataValidationException(s"cannot find ad request parser for $ct")
    }
  }

  /**
    * Generates byte representation of [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]]
    * in the specified format.
    *
    * @param response [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]]
    */
  override def write(response: AdResponse): Array[Byte] = {
    adResponseWriters.get(response.ct) match {
      case Some(writer) => writer.write(response)
      case None => throw new DataValidationException(
        s"cannot find ad response writer for ${response.ct}")
    }
  }
}

