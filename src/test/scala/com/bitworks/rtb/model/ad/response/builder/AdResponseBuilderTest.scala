package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder AdResponseBuilder]].
  *
  * @author Pavel Tomskikh
  */
class AdResponseBuilderTest extends FlatSpec with Matchers {

  "AdResponseBuilder" should "build AdResponse correctly" in {
    val imp = AdResponseImp("123", "admarkup", 1)
    val error = Error(33, "some error")
    val adResponse = AdResponse("123", Some(imp), Some(error))

    var builder = AdResponseBuilder(adResponse.id)
    adResponse.imp.foreach(imp => builder = builder.withImp(imp))
    adResponse.error.foreach(error => builder = builder.withError(error))

    val builtAdResponse = builder.build

    builtAdResponse shouldBe adResponse
  }

  it should "build AdResponse with default values correctly" in {
    val adResponse = AdResponse("123", None, None)
    val builtAdResponse = AdResponseBuilder(adResponse.id).build

    builtAdResponse shouldBe adResponse
  }
}
