package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.Bid
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class BidBuilderTest extends FlatSpec with Matchers{

  "BidBuilder" should "build Bid with required attributes" in {
    val bid = BidBuilder("one", "two", "12.5").build

    bid.id shouldBe "one"
    bid.impid shouldBe "two"
    bid.price shouldBe BigDecimal("12.5")
    bid.cid shouldBe None
  }

  it should "build Bid with enum attributes" in {
    val bid = BidBuilder("one", "two", "12.5")
      .withAttr(Set(1, 2))
      .build

    bid.attr shouldBe defined
    bid.attr.orNull should contain theSameElementsAs
      Set(2, 1)
  }

  it should "correctly build whole Bid" in {
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
      .withDealid("dealid")
      .withH(42)
      .withW(12)
      .withExt("Just any")
      .build

    buildedBid shouldBe bid
  }

}
