package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.response.{Error, ErrorCode}
import com.bitworks.rtb.model.http.{ContentTypeModel, NoContentType}
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.response.BidResponse
import com.bitworks.rtb.service.DataValidationException
import com.bitworks.rtb.service.parser.BidResponseParser
import com.bitworks.rtb.service.writer.BidRequestWriter

/**
  * A converter for bid models.
  *
  * @author Egor Ilchenko
  */
trait BidModelConverter {

  /**
    * Parses binary representation of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * in the specified format.
    *
    * @param bytes input bytes
    * @param ct    [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    * @throws DataValidationException in case of missing handler or invalid bytes
    */
  def parse(bytes: Array[Byte], ct: ContentTypeModel): BidResponse

  /**
    * Generates byte representation of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * in the specified format.
    *
    * @param request [[com.bitworks.rtb.model.request.BidRequest BidRequest]]
    * @param ct      [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    */
  def write(request: BidRequest, ct: ContentTypeModel): Array[Byte]
}

/**
  * An implementation of a converter for bid models.
  *
  * @author Egor Ilchenko
  */
class BidModelConverterImpl(
    bidResponseParsers: Map[ContentTypeModel, BidResponseParser],
    bidRequestWriters: Map[ContentTypeModel, BidRequestWriter]) extends BidModelConverter {

  /**
    * Parses binary representation of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * in the specified format.
    *
    * @param bytes input bytes
    * @param ct    [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    * @throws DataValidationException in case of missing handler or invalid bytes
    */
  override def parse(bytes: Array[Byte], ct: ContentTypeModel) = {
    bidResponseParsers.get(ct) match {
      case Some(parser) => parser.parse(bytes)
      case None => throw new DataValidationException(ErrorCode.INCORRECT_HEADER_VALUE)
    }
  }

  /**
    * Generates byte representation of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * in the specified format.
    *
    * @param request [[com.bitworks.rtb.model.request.BidRequest BidRequest]]
    * @param ct      [[com.bitworks.rtb.model.http.ContentTypeModel ContentTypeModel]]
    */
  override def write(request: BidRequest, ct: ContentTypeModel) = {
    bidRequestWriters.get(ct) match {
      case Some(writer) => writer.write(request)
      case None => throw new DataValidationException(ErrorCode.INCORRECT_HEADER_VALUE)
    }
  }
}

