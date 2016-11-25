package com.bitworks.rtb.service.writer

import com.bitworks.rtb.model.http.{ContentType, HttpHeaderModel, JSON}
import com.bitworks.rtb.service.DataValidationException
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.easymock.EasyMockSugar

/**
  * Test for [[com.bitworks.rtb.service.writer.AdResponseWriterFactory AdResponseWriterFactory]].
  *
  * @author Egor Ilchenko
  */
class AdResponseWriterFactoryTest extends FlatSpec with Matchers with EasyMockSugar {

  "AdResponseWriterFactory" should "create writer correctly" in {
    val factory = AdResponseWriterFactoryImpl()

    factory shouldBe a[AdResponseWriterFactoryImpl]

  }

  it should "return writer for JSON content type" in {
    val jsonParserMock = mock[AdResponseJsonWriter]
    val factory = new AdResponseWriterFactoryImpl(jsonParserMock)

    val result = factory.getWriter(JSON)

    result should be theSameInstanceAs jsonParserMock
  }

  it should "throw exception if writer not found" in {
    val factory = AdResponseWriterFactoryImpl()
    case object Another extends ContentType {
      override def header: HttpHeaderModel = HttpHeaderModel("Content-Type", "notjson")
    }

    an[DataValidationException] shouldBe thrownBy {
      factory.getWriter(Another)
    }

  }

}
