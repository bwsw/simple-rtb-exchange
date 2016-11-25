package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response._
import com.bitworks.rtb.model.http.JSON
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder AdResponseBuilder]].
  *
  * @author Pavel Tomskikh
  */
class AdResponseBuilderTest extends FlatSpec with Matchers {

  "AdResponseBuilder" should "build AdResponse correctly" in {
    val imp = Imp("123", "admarkup", 1)
    val error = Error(33, "some error")
    val ct = JSON
    val adResponse = AdResponse("123", Some(Seq(imp)), Some(error), JSON)

    var builder = AdResponseBuilder(adResponse.id, adResponse.ct)
    adResponse.imp.foreach(imp => builder = builder.withImp(imp))
    adResponse.error.foreach(error => builder = builder.withError(error))

    val builtAdResponse = builder.build

    builtAdResponse shouldBe adResponse
  }

  it should "build AdResponse with default values correctly" in {
    val adResponse = AdResponse("123", None, None, JSON)
    val builtAdResponse = AdResponseBuilder(adResponse.id, adResponse.ct).build

    builtAdResponse shouldBe adResponse
  }
}
