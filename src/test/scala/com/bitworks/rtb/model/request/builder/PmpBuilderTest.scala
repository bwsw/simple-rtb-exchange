package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Pmp
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.PmpBuilder PmpBuilder]].
  *
  * @author Egor Ilchenko
  */
class PmpBuilderTest extends FlatSpec with Matchers {

  "PmpBuilder" should "build Pmp correctly" in {
    val pmp = Pmp(Some(1), Some(Seq.empty), Some("string"))

    val builder = PmpBuilder()
    pmp.privateAuction.foreach(privateAuction => builder.withPrivateAuction(privateAuction))
    pmp.deals.foreach(deals => builder.withDeals(deals))
    pmp.ext.foreach(ext => builder.withExt(ext))

    val builtPmp = builder.build

    builtPmp shouldBe pmp
  }

}
