package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.Bid
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.Bid]]
  *
  * @author Egor Ilchenko
  *
  */
class BidBuilderTest extends FlatSpec with Matchers{

  "BidBuilder" should "build Bid correctly" in {
    val bid = Bid("id", "impid", BigDecimal("42.42"), Some("adid"), Some("nurl"),
      Some("adm"), Some(Seq("adomain")), Some("bundle"), Some("iurl"), Some("cid"),
      Some("crid"), Some(Seq("cat")), Some(Set(1)),
      Some("dealid"), Some(42), Some(12), Some("Just any"))

    val buildedBid = BidBuilder("id", "impid", "42.42")
      .withAdid("adid")
      .withNurl("nurl")
      .withAdm("adm")
      .withAdomain(Seq("adomain"))
      .withBundle("bundle")
      .withIurl("iurl")
      .withCid("cid")
      .withCrid("crid")
      .withCat(Seq("cat"))
      .withAttr(Set(1))
      .withDealId("dealid")
      .withH(42)
      .withW(12)
      .withExt("Just any")
      .build

    buildedBid shouldBe bid
  }

}
