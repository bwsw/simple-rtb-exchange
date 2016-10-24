package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Regs
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.RegsBuilder RegsBuilder]].
  *
  * @author Egor Ilchenko
  */
class RegsBuilderTest extends FlatSpec with Matchers {

  "RegsBuilder" should "build Regs correctly" in {
    val regs = Regs(Some(42), Some("ext"))

    val builder = RegsBuilder()
    regs.coppa.foreach(coppa => builder.withCoppa(coppa))
    regs.ext.foreach(ext => builder.withExt(ext))

    val builtRegs = builder.build

    builtRegs shouldBe regs
  }

}
