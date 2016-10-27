package com.bitworks.rtb.writer.response

import com.bitworks.rtb.model.ad.response.{AdResponse, Error, Imp}
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

/**
  * Test for [[com.bitworks.rtb.writer.response.AdResponseJsonWriter AdResponseJsonWriter]].
  *
  * @author Egor Ilchenko
  */
class AdResponseJsonWriterTest extends FlatSpec with Matchers with OneInstancePerTest {

  val mapper = new ObjectMapper
  val writer = new AdResponseJsonWriter

  "AdResponseJsonWriter" should "write json correctly" in {
    val imps = Seq(Imp("id1", "adm1", 1), Imp("id2", "adm2", 2))
    val error = Error(24, "error message")
    val adResponse = AdResponse("id", Some(imps), Some(error))

    val json = writer.write(adResponse)
    val path = getClass.getResource("/com.bitworks.rtb.writer.response/adresponse.json").getPath
    val expectedJson = (io.Source fromFile path).mkString

    val jsonNode = mapper.readValue(json, classOf[JsonNode])
    val expectedJsonNode = mapper.readValue(expectedJson, classOf[JsonNode])

    jsonNode shouldBe expectedJsonNode
  }

  it should "write json without required fields" in {
    val adResponse = AdResponse("id", None, None)

    val json = writer.write(adResponse)
    val expectedJson = """{"id":"id"}"""

    val jsonNode = mapper.readValue(json, classOf[JsonNode])
    val expectedJsonNode = mapper.readValue(expectedJson, classOf[JsonNode])

    jsonNode shouldBe expectedJsonNode
  }
}
