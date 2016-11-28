package com.bitworks.rtb.service.parser

import com.bitworks.rtb.model._
import com.bitworks.rtb.model.ad.response.ErrorCode
import com.bitworks.rtb.model.request._
import com.bitworks.rtb.service.DataValidationException
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

/**
  * Test for [[com.bitworks.rtb.service.parser.AdRequestJsonParser AdRequestJsonParser]].
  *
  * @author Egor Ilchenko
  */
class AdRequestJsonParserTest extends FlatSpec with Matchers {

  "AdRequestJsonParser" should "throw exception if JSON syntax is incorrect" in {
    val incorrectJson = "incorrectJson".getBytes
    val parser = new AdRequestJsonParser

    val thrown = the [DataValidationException] thrownBy parser.parse(incorrectJson)
    thrown.getError.code shouldBe ErrorCode.INCORRECT_REQUEST
  }

  it should "throw exception if datatype in JSON doesn`t match model datatype" in {
    val incorrectJson = """{"imp": "wrong", "device":"wrong too"}""".getBytes
    val parser = new AdRequestJsonParser

    val thrown = the [DataValidationException] thrownBy parser.parse(incorrectJson)
    thrown.getError.code shouldBe ErrorCode.INCORRECT_REQUEST
  }

  it should "throw exception if id is missed" in {
    val incorrectJson = """{"imp": []}""".getBytes
    val parser = new AdRequestJsonParser

    val thrown = the [DataValidationException] thrownBy parser.parse(incorrectJson)
    thrown.getError.code shouldBe ErrorCode.INCORRECT_REQUEST
  }

  it should "throw exception if imp is missed" in {
    val incorrectJson = """{"id": "id"}""".getBytes
    val parser = new AdRequestJsonParser

    val thrown = the [DataValidationException] thrownBy parser.parse(incorrectJson)
    thrown.getError.code shouldBe ErrorCode.INCORRECT_REQUEST
  }

  it should "correctly parse JSON without optional fields" in {
    val json = """{ "id": "id", "imp": [] }""".getBytes
    val parser = new AdRequestJsonParser

    val expectedModel = ad.request.AdRequest(
      "id",
      Seq.empty,
      None,
      None,
      None,
      None,
      ad.request.builder.AdRequestBuilder.Test,
      None,
      None)

    val parsedModel = parser.parse(json)

    parsedModel shouldBe expectedModel
  }

  it should "correctly parse JSON" in {
    val expectedModel = getAdRequestModel
    val parser = new AdRequestJsonParser

    val path = getClass.getResource("adrequest.json").getPath
    val json = Source.fromFile(path).mkString.getBytes

    val parsedModel = parser.parse(json)

    parsedModel shouldBe expectedModel
  }

  private def getAdRequestModel = {
    val banner = Banner(
      Some(1),
      Some(2),
      Some(3),
      Some(4),
      Some(5),
      Some(6),
      Some("id"),
      Some(Seq(7)),
      Some(Seq(8)),
      Some(9),
      Some(Seq("mime")),
      Some(10),
      Some(Seq(11)),
      Some(Seq(12)),
      None)

    val video = Video(
      Seq("mime"),
      Some(1),
      Some(2),
      Some(3),
      Some(Seq(4)),
      Some(5),
      Some(6),
      Some(7),
      Some(8),
      Some(9),
      Some(Seq(10)),
      Some(11),
      Some(12),
      Some(13),
      14,
      Some(Seq(15)),
      Some(Seq(16)),
      Some(17),
      Some(Seq.empty),
      Some(Seq(18)),
      Some(Seq(19)),
      None)

    val native = Native(
      "request",
      Some("ver"),
      Some(Seq(1)),
      Some(Seq(2)),
      None)

    val producer = Producer(
      Some("123"),
      Some("prod"),
      Some(Seq("IAB1-2")),
      Some("prod.com"),
      None)

    val content = Content(
      Some("123"),
      Some(5),
      Some("title"),
      Some("auto"),
      Some("s3"),
      Some(producer),
      Some("content.com"),
      Some(Seq("IAB1-3", "IAB5-7")),
      Some(2),
      Some(1),
      Some("MPAA"),
      Some("middle"),
      Some(2),
      Some("kw1,kw2"),
      Some(1),
      Some(1),
      Some(30),
      Some("ru"),
      Some(0),
      None)

    val site = ad.request.Site(
      2,
      Some(Seq("sectioncat")),
      Some(Seq("pagecat")),
      Some("page"),
      Some("ref"),
      Some("search"),
      Some(1),
      Some(content))

    val app = ad.request.App(
      3,
      Some(Seq("section")),
      Some(Seq("page")),
      Some(content))

    val geo = Geo(
      Some(42.42f),
      Some(24.24f),
      Some(1),
      Some("country"),
      Some("region"),
      Some("regionFips"),
      Some("metro"),
      Some("city"),
      Some("zip"),
      Some(14),
      None)

    val device = Device(
      Some("ua"),
      Some(geo),
      Some(1),
      Some(2),
      Some("ip"),
      Some("ipv6"),
      Some(8),
      Some("make"),
      Some("model"),
      Some("os"),
      Some("osv"),
      Some("hwv"),
      Some(3),
      Some(4),
      Some(5),
      Some(6),
      Some(7),
      Some("flashver"),
      Some("language"),
      Some("carrier"),
      Some(9),
      Some("ifa"),
      Some("didsha1"),
      Some("didmd5"),
      Some("dpidsha1"),
      Some("dpidmd5"),
      Some("macsha1"),
      Some("macmd5"),
      None)

    val user = ad.request.User(
      Some("id"),
      Some(2000),
      Some("M"),
      Some("keywords"),
      Some(geo))

    val adRequestImp = ad.request.Imp("id", Some(banner), Some(video), Some(native))

    val regs = Regs(Some(42), None)

    ad.request.AdRequest(
      "id",
      Seq(adRequestImp),
      Some(site),
      Some(app),
      Some(device),
      Some(user),
      1,
      Some(2),
      Some(regs))
  }

}
