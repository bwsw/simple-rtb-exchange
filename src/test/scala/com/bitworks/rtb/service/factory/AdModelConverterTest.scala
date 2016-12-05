package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.response.ErrorCode
import com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder
import com.bitworks.rtb.model.http.{Avro, ContentTypeModel, Json}
import com.bitworks.rtb.service.DataValidationException
import com.bitworks.rtb.service.parser.AdRequestParser
import com.bitworks.rtb.service.writer.AdResponseWriter
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.service.factory.AdModelConverter AdModelConverter]].
  *
  * @author Egor Ilchenko
  */
class AdModelConverterTest extends FlatSpec with Matchers with EasyMockSugar {

  "AdModelConverter" should "parse ad request using suitable parser" in {
    val bytes = new Array[Byte](0)
    val request = AdRequestBuilder("id", Seq.empty, Json).build

    val parserMock = mock[AdRequestParser]
    expecting {
      parserMock.parse(bytes).andReturn(request).times(1)
    }

    whenExecuting(parserMock) {
      val binding: Map[ContentTypeModel, AdRequestParser] = Map(Json -> parserMock)
      val converter = new AdModelConverterImpl(binding, Map.empty)
      val result = converter.parse(bytes, Json)

      result shouldBe request
    }
  }

  it should "throw exception if parser not found" in {
    val parser = mock[AdRequestParser]
    val converter = new AdModelConverterImpl(Map(Avro -> parser), Map.empty)

    val thrown = the[DataValidationException] thrownBy {
      converter.parse(new Array[Byte](0), Json)
    }
    thrown.getError shouldBe ErrorCode.INCORRECT_HEADER_VALUE
  }

  it should "write ad response using suitable writer" in {
    val response = AdResponseBuilder(Json).build
    val bytes = new Array[Byte](0)
    val writerMock = mock[AdResponseWriter]
    expecting {
      writerMock.write(response).andReturn(bytes).times(1)
    }
    whenExecuting(writerMock) {
      val binding: Map[ContentTypeModel, AdResponseWriter] = Map(Json -> writerMock)
      val converter = new AdModelConverterImpl(Map.empty, binding)
      val result = converter.write(response)

      result shouldBe bytes
    }
  }

  it should "throw exception if writer not found" in {
    val response = AdResponseBuilder(Json).build
    val writer = mock[AdResponseWriter]
    val converter = new AdModelConverterImpl(Map.empty, Map(Avro -> writer))

    val thrown = the[DataValidationException] thrownBy {
      converter.write(response)
    }
    thrown.getError shouldBe ErrorCode.INCORRECT_HEADER_VALUE
  }

}
