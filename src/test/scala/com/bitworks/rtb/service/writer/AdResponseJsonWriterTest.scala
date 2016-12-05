package com.bitworks.rtb.service.writer

import com.bitworks.rtb.model.ad.response.{AdResponse, Error, ErrorCode, Imp}
import com.bitworks.rtb.model.http.Json
import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

/**
  * Test for [[com.bitworks.rtb.service.writer.AdResponseJsonWriter AdResponseJsonWriter]].
  *
  * @author Egor Ilchenko
  */
class AdResponseJsonWriterTest extends FlatSpec with Matchers {

  val mapper = new ObjectMapper
  val writer = new AdResponseJsonWriter

  "AdResponseJsonWriter" should "write json correctly" in {
    val imps = Seq(Imp("id1", "adm1", 1), Imp("id2", "adm2", 2))
    val error = Error(ErrorCode.UNKNOWN_ERROR.id, "error message")
    val adResponse = AdResponse(Some("id"), Some(imps), Some(error), Json)

    val json = writer.write(adResponse)
    val path = getClass.getResource("adresponse.json").getPath
    val expectedJson = (Source fromFile path).mkString

    val jsonNode = mapper.readTree(json)
    val expectedJsonNode = mapper.readTree(expectedJson)

    jsonNode shouldBe expectedJsonNode
  }

  it should "write json without optional fields correctly" in {
    val adResponse = AdResponse(None, None, None, Json)

    val json = writer.write(adResponse)
    val expectedJson = "{}"

    val jsonNode = mapper.readTree(json)
    val expectedJsonNode = mapper.readTree(expectedJson)

    jsonNode shouldBe expectedJsonNode
  }
}
