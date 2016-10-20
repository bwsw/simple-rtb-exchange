package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Deal
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class DealBuilderTest extends FlatSpec with Matchers{

  "DealBuilder" should "build Deal correctly" in {
    val deal = Deal("id", BigDecimal(1), "EUR", Some(1), Some(Seq("wseat")),
      Some(Seq("wadomain")), Some("string"))

    val buildedDeal = DealBuilder("id")
        .withBidfloor("1")
        .withBidfloorcur("EUR")
        .withAt(1)
        .withWseat(Seq("wseat"))
        .withWadomain(Seq("wadomain"))
        .withExt("string")
        .build

    buildedDeal shouldBe deal
  }

}