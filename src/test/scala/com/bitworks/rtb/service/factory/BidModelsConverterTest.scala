package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.http.{Avro, ContentTypeModel, Json}
import com.bitworks.rtb.model.request.builder.BidRequestBuilder
import com.bitworks.rtb.model.response.builder.BidResponseBuilder
import com.bitworks.rtb.service.DataValidationException
import com.bitworks.rtb.service.parser.BidResponseParser
import com.bitworks.rtb.service.writer.BidRequestWriter
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.service.factory.BidModelsConverter BidModelsConverter]].
  *
  * @author Egor Ilchenko
  */
class BidModelsConverterTest extends FlatSpec with Matchers with EasyMockSugar {

  "BidModelsConverter" should "parse bid response using suitable parser" in {
    val bytes = new Array[Byte](0)
    val response = BidResponseBuilder("id", Nil).build

    val parserMock = mock[BidResponseParser]
    expecting {
      parserMock.parse(bytes).andReturn(response).times(1)
    }

    whenExecuting(parserMock) {
      val binding: Map[ContentTypeModel, BidResponseParser] = Map(Json -> parserMock)
      val converter = new BidModelsConverterImpl(binding, Map.empty)
      val result = converter.parse(bytes, Json)

      result shouldBe response
    }
  }

  it should "throw exception if parser not found" in {
    val parser = mock[BidResponseParser]
    val converter = new BidModelsConverterImpl(Map(Avro -> parser), Map.empty)

    an[DataValidationException] should be thrownBy {
      converter.parse(new Array[Byte](0), Json)
    }
  }

  it should "write bid request using suitable writer" in {
    val request = BidRequestBuilder("id", Nil).build
    val bytes = new Array[Byte](0)
    val writerMock = mock[BidRequestWriter]
    expecting {
      writerMock.write(request).andReturn(bytes).times(1)
    }
    whenExecuting(writerMock) {
      val binding: Map[ContentTypeModel, BidRequestWriter] = Map(Json -> writerMock)
      val converter = new BidModelsConverterImpl(Map.empty, binding)
      val result = converter.write(request, Json)

      result shouldBe bytes
    }
  }

  it should "throw exception if writer not found" in {
    val request = BidRequestBuilder("id", Nil).build
    val writerMock = mock[BidRequestWriter]
    val converter = new BidModelsConverterImpl(Map.empty, Map(Avro -> writerMock))
    an[DataValidationException] should be thrownBy {
      converter.write(request, Json)
    }
  }

}
