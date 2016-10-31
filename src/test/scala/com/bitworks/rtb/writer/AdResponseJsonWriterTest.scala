package com.bitworks.rtb.writer

import com.bitworks.rtb.model.ad.response.{AdResponse, Error, Imp}
import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.writer.AdResponseJsonWriter AdResponseJsonWriter]].
  *
  * @author Egor Ilchenko
  */
class AdResponseJsonWriterTest extends FlatSpec with Matchers {

  val mapper = new ObjectMapper
  val writer = new AdResponseJsonWriter

  "AdResponseJsonWriter" should "write json correctly" in {
    val imps = Seq(Imp("id1", "adm1", 1), Imp("id2", "adm2", 2))
    val error = Error(24, "error message")
    val adResponse = AdResponse("id", Some(imps), Some(error))

    val json = writer.write(adResponse)
    val path = getClass.getResource("adresponse.json").getPath
    val expectedJson = (io.Source fromFile path).mkString

    val jsonNode = mapper.readTree(json)
    val expectedJsonNode = mapper.readTree(expectedJson)

    jsonNode shouldBe expectedJsonNode
  }

  it should "write json without required fields" in {
    val adResponse = AdResponse("id", None, None)

    val json = writer.write(adResponse)
    val expectedJson = """{"id":"id"}"""

    val jsonNode = mapper.readTree(json)
    val expectedJsonNode = mapper.readTree(expectedJson)

    jsonNode shouldBe expectedJsonNode
  }
}
