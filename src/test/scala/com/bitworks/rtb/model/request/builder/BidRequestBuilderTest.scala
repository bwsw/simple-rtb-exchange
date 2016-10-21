package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.BidRequestBuilder]]
  *
  * Created on: 10/20/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class BidRequestBuilderTest extends FlatSpec with Matchers {

  val imp = Seq(Imp("id", None, None, None, None, None, 0, None,
    BigDecimal("0"), "USD", None, None, None, None))
  val site = Site(None, None, None, None, None, None, None, None,
    None, None, None, None, None, None, None)
  val app = App(None, None, None, None, None, None, None, None,
    None, None, None, None, None, None, None)
  val device = Device(None, None, None, None, None, None, None, None,
    None, None, None, None, None, None, None, None, None, None, None,
    None, None, None, None, None, None, None, None, None, None)
  val user = User(None, None, None, None, None, None, None, None, None)
  val regs = Regs(1, None)

  "BidRequestBuilder" should "build BidRequest with default parameters correctly" in {
    val bidRequest = BidRequest("req", imp, None, None, None, None, Some(0),
      Some(2), None, None, Some(0), None, None, None, None, None)
    val buildedBidRequest = BidRequestBuilder("req", imp).build

    buildedBidRequest shouldBe bidRequest
  }

  it should "build BidRequest with optional parameters correctly" in {
    val bidRequest = BidRequest(
      "req",
      imp,
      Some(site),
      Some(app),
      Some(device),
      Some(user),
      Some(1),
      Some(3),
      Some(10),
      Some(Seq("b1", "b2")),
      Some(1),
      Some(Seq("c1", "c2")),
      Some(Seq("IAB3-4")),
      Some(Seq("block1", "block2")),
      Some(regs),
      Some("ext"))
    val buildedBidRequest = BidRequestBuilder("req", imp)
        .withSite(site)
        .withApp(app)
        .withDevice(device)
        .withUser(user)
        .withTest(1)
        .withAt(3)
        .withTmax(10)
        .withWseat(Seq("b1", "b2"))
        .withAllImps(1)
        .withCur(Seq("c1", "c2"))
        .withBcat(Seq("IAB3-4"))
        .withBadv(Seq("block1", "block2"))
        .withRegs(regs)
        .withExt("ext")
        .build

    buildedBidRequest shouldBe bidRequest
  }


}
