package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.response.builder.AdResponseImpBuilder AdResponseImpBuilder]].
  *
  * @author Pavel Tomskikh
  */
class AdResponseImpBuilderTest extends FlatSpec with Matchers {

  "AdResponseImpBuilder" should "build AdResponse correctly" in {
    val adResponseImp = AdResponseImp("123", "admarkup", 2)
    val builtAdResponse =
      AdResponseImpBuilder(
        adResponseImp.id,
        adResponseImp.adm,
        adResponseImp.`type`)
          .build

    builtAdResponse shouldBe adResponseImp
  }
}
