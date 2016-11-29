package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder
import com.bitworks.rtb.model.http.{Avro, ContentTypeModel, Json}
import com.bitworks.rtb.service.DataValidationException
import com.bitworks.rtb.service.parser.AdRequestParser
import com.bitworks.rtb.service.writer.AdResponseWriter
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.service.factory.AdModelsConverter AdModelsConverter]].
  *
  * @author Egor Ilchenko
  */
class AdModelsConverterTest extends FlatSpec with Matchers with EasyMockSugar {

  "AdModelsConverter" should "parse ad request using suitable parser" in {
    val bytes = new Array[Byte](0)
    val request = AdRequestBuilder("id", Seq.empty, Json).build

    val parserMock = mock[AdRequestParser]
    expecting {
      parserMock.parse(bytes).andReturn(request).times(1)
    }

    whenExecuting(parserMock) {
      val binding: Map[ContentTypeModel, AdRequestParser] = Map(Json -> parserMock)
      val converter = new AdModelsConverterImpl(binding, Map.empty)
      val result = converter.parse(bytes, Json)

      result shouldBe request
    }
  }

  it should "throw exception if parser not found" in {
    val parser = mock[AdRequestParser]
    val converter = new AdModelsConverterImpl(Map(Avro -> parser), Map.empty)

    an[DataValidationException] should be thrownBy {
      converter.parse(new Array[Byte](0), Json)
    }
  }

  it should "write ad response using suitable writer" in {
    val response = AdResponseBuilder("id", Json).build
    val bytes = new Array[Byte](0)
    val writerMock = mock[AdResponseWriter]
    expecting {
      writerMock.write(response).andReturn(bytes).times(1)
    }
    whenExecuting(writerMock) {
      val binding: Map[ContentTypeModel, AdResponseWriter] = Map(Json -> writerMock)
      val converter = new AdModelsConverterImpl(Map.empty, binding)
      val result = converter.write(response)

      result shouldBe bytes
    }
  }

  it should "throw exception if writer not found" in {
    val response = AdResponseBuilder("id", Json).build
    val writer = mock[AdResponseWriter]
    val converter = new AdModelsConverterImpl(Map.empty, Map(Avro -> writer))
    an[DataValidationException] should be thrownBy {
      converter.write(response)
    }
  }

}
