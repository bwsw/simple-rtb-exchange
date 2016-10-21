package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Deal
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.Deal]]
  *
  * @author Egor Ilchenko
  *
  */
class DealBuilderTest extends FlatSpec with Matchers{

  "DealBuilder" should "build Deal correctly" in {
    val deal = Deal("id", BigDecimal(1), "EUR", Some(1), Some(Seq("wseat")),
      Some(Seq("wadomain")), Some("string"))

    val buildedDeal = DealBuilder("id")
        .withBidFloor("1")
        .withBidFloorCur("EUR")
        .withAt(1)
        .withWseat(Seq("wseat"))
        .withWadomain(Seq("wadomain"))
        .withExt("string")
        .build

    buildedDeal shouldBe deal
  }

  it should "build Deal with default values" in {
    val deal = Deal("id", BigDecimal("0"), "USD", None, None, None, None)

    val buildedDeal = DealBuilder("id").build

    buildedDeal shouldBe deal
  }

}