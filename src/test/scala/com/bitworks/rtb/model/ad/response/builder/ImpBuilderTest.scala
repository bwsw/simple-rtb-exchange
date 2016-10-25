package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.response.builder.ImpBuilder ImpBuilder]].
  *
  * @author Pavel Tomskikh
  */
class ImpBuilderTest extends FlatSpec with Matchers {

  "ImpBuilder" should "build Imp correctly" in {
    val adResponseImp = Imp("123", "admarkup", 2)
    val builtAdResponse =
      ImpBuilder(
        adResponseImp.id,
        adResponseImp.adm,
        adResponseImp.`type`)
          .build

    builtAdResponse shouldBe adResponseImp
  }
}
