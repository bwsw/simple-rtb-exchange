package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.Bid
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.builder.BidBuilder BidBuilder]].
  *
  * @author Egor Ilchenko
  */
class BidBuilderTest extends FlatSpec with Matchers {

  "BidBuilder" should "build Bid correctly" in {
    val bid = Bid(
      "id",
      "impid",
      BigDecimal("42.42"),
      Some("adid"),
      Some("nurl"),
      Some("adm"),
      Some(Seq("adomain")),
      Some("bundle"),
      Some("iurl"),
      Some("cid"),
      Some("crid"),
      Some(Seq("cat")),
      Some(Set(1)),
      Some("dealid"),
      Some(42),
      Some(12),
      Some("Just any"))

    var builder = BidBuilder(bid.id, bid.impId, bid.price)
    bid.adId.foreach(adId => builder = builder.withAdId(adId))
    bid.nurl.foreach(nurl => builder = builder.withNurl(nurl))
    bid.adm.foreach(adm => builder = builder.withAdm(adm))
    bid.adomain.foreach(adomain => builder = builder.withAdomain(adomain))
    bid.bundle.foreach(bundle => builder = builder.withBundle(bundle))
    bid.iurl.foreach(iurl => builder = builder.withIurl(iurl))
    bid.cid.foreach(cid => builder = builder.withCid(cid))
    bid.crid.foreach(crid => builder = builder.withCrid(crid))
    bid.cat.foreach(cat => builder = builder.withCat(cat))
    bid.attr.foreach(attr => builder = builder.withAttr(attr))
    bid.dealId.foreach(dealId => builder = builder.withDealId(dealId))
    bid.h.foreach(h => builder = builder.withH(h))
    bid.w.foreach(w => builder = builder.withW(w))
    bid.ext.foreach(ext => builder = builder.withExt(ext))

    val builtBid = builder.build

    builtBid shouldBe bid
  }

  it should "build Bid with default values correctly" in {
    val bid = Bid(
      "id",
      "impid",
      BigDecimal("42.42"),
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None)

    val builtBid = BidBuilder(bid.id, bid.impId, bid.price).build

    builtBid shouldBe bid
  }

}
