package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Regs
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.Regs]]
  *
  * @author Egor Ilchenko
  *
  */
class RegsBuilderTest extends FlatSpec with Matchers{

  "RegsBuilder" should "build Regs correctly" in {
    val regs = Regs(42, Some("ext"))

    val buildedRegs = RegsBuilder()
        .withCoppa(42)
        .withExt("ext")
        .build

    buildedRegs shouldBe regs
  }

}
