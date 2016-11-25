package com.bitworks.rtb.service.writer

import com.bitworks.rtb.model.http.{ContentType, HttpHeaderModel, JSON}
import com.bitworks.rtb.service.DataValidationException
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.easymock.EasyMockSugar

/**
  * Test for [[com.bitworks.rtb.service.writer.BidRequestWriterFactory BidRequestWriterFactory]].
  *
  * @author Egor Ilchenko
  */
class BidRequestWriterFactoryTest extends FlatSpec with Matchers with EasyMockSugar {

  "BidRequestWriterFactory" should "create writer correctly" in {
    val factory = BidRequestWriterFactoryImpl()

    factory shouldBe a[BidRequestWriterFactoryImpl]

  }

  it should "return writer for JSON content type" in {
    val jsonParserMock = mock[BidRequestJsonWriter]
    val factory = new BidRequestWriterFactoryImpl(jsonParserMock)

    val result = factory.getWriter(JSON)

    result should be theSameInstanceAs jsonParserMock
  }

  it should "throw exception if writer not found" in {
    val factory = BidRequestWriterFactoryImpl()
    case object Another extends ContentType {
      override def header: HttpHeaderModel = HttpHeaderModel("Content-Type", "notjson")
    }

    an[DataValidationException] shouldBe thrownBy {
      factory.getWriter(Another)
    }

  }

}
