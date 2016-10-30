package com.bitworks.rtb.parser

import com.bitworks.rtb.model.response.{Bid, BidResponse, SeatBid}
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

/**
  * Test for [[BidResponseJsonParser BidResponseJsonParser]].
  *
  * @author Pavel Tomskikh
  */
class BidResponseJsonParserTest extends FlatSpec with Matchers {

  "BidResponseJsonParser" should "throw exception if JSON format is incorrect" in {
    val ircorrectString = "not JSON"
    an[DataValidationException] should be thrownBy
        BidResponseJsonParser.parse(ircorrectString)
  }

  it should "thrown exception if datatype in JSON is wrong" in {
    val ircorrectString = """{ "id":"123", "seatbid":"notseatbid"} """
    an[DataValidationException] should be thrownBy
        BidResponseJsonParser.parse(ircorrectString)
  }

  it should "throw exception if required fields are missing" in {
    val ircorrectString = """{ "id":"123" } """
    an[DataValidationException] should be thrownBy
        BidResponseJsonParser.parse(ircorrectString)
  }

  private def getCorrectBidResponse = {
    val bid1 = Bid(
      "1",
      "102",
      BigDecimal(9.43),
      None,
      Some("http://adserver.com/winnotice?impid=102"),
      None,
      Some(Seq("advertiserdomain.com")),
      None,
      Some("http://adserver.com/pathtosampleimage"),
      Some("campaign111"),
      Some("creative112"),
      None,
      Some(Set(1, 2, 3)),
      None,
      None,
      None,
      None)
    val bid2 = Bid(
      "2",
      "73",
      BigDecimal(11.05),
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None)
    val bid3 = Bid(
      "3",
      "7",
      BigDecimal(13),
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None)
    val seatBid1 = SeatBid(
      Seq(bid1, bid2),
      Some("512"),
      1,
      None
    )
    val seatBid2 = SeatBid(
      Seq(bid3),
      Some("s11"),
      0,
      None
    )
    val bidResponse = BidResponse(
      "1234567890",
      Seq(seatBid1, seatBid2),
      Some("abc1123"),
      "RUB",
      None,
      None,
      None
    )

    bidResponse
  }

  it should "correctly parse JSON" in {
    val expectedBidResponse = getCorrectBidResponse
    val path = getClass.getResource("/com/bitworks/rtb/parser/bidResponseExample.json").getPath
    val json = Source.fromFile(path).mkString
    val parsedBidResponse = BidResponseJsonParser.parse(json)

    parsedBidResponse shouldBe expectedBidResponse
  }
}
