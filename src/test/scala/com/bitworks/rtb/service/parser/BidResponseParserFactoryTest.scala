package com.bitworks.rtb.service.parser

import com.bitworks.rtb.model.http.HttpHeaderModel
import com.bitworks.rtb.service.DataValidationException
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.easymock.EasyMockSugar

/**
  * Test for [[com.bitworks.rtb.service.parser.BidResponseParserFactory BidResponseParserFactory]].
  *
  * @author Egor Ilchenko
  */
class BidResponseParserFactoryTest extends FlatSpec with Matchers with EasyMockSugar {

  "BidResponseParserFactory" should "create parser correctly" in {
    val factory = BidResponseParserFactoryImpl()

    factory shouldBe a[BidResponseParserFactoryImpl]

  }

  it should "return parser for JSON headers" in {
    val jsonParserMock = mock[BidResponseJsonParser]
    val factory = new BidResponseParserFactoryImpl(jsonParserMock)

    val header = HttpHeaderModel("Content-Type", "application/json")
    val result = factory.getParser(Seq(header))

    result should be theSameInstanceAs jsonParserMock
  }

  it should "throw exception if parser not found" in {
    val factory = BidResponseParserFactoryImpl()

    an[DataValidationException] shouldBe thrownBy {
      factory.getParser(Seq.empty)
    }

    an[DataValidationException] shouldBe thrownBy {
      factory.getParser(Seq(HttpHeaderModel("key", "value")))
    }

  }

}
