package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.BidRequestBuilder BidRequestBuilder]].
  *
  * @author Pavel Tomskikh
  */
class BidRequestBuilderTest extends FlatSpec with Matchers {

  val imp = Seq(
    Imp(
      "id",
      None,
      None,
      None,
      None,
      None,
      0,
      None,
      BigDecimal("0"),
      "USD",
      None,
      None,
      None,
      None))
  val site =
    Site(
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
      None,
      None)
  val app =
    App(
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
      None,
      None)
  val device =
    Device(
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
      None,
      None,
      None)
  val user =
    User(
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None,
      None)
  val regs = Regs(None, None)

  "BidRequestBuilder" should "build BidRequest with default values correctly" in {
    val bidRequest = BidRequest("req", imp, None, None, None, None, BidRequestBuilder.Test,
      BidRequestBuilder.At, None, None, BidRequestBuilder.AllImps, None, None, None, None, None)
    val builtBidRequest = BidRequestBuilder(bidRequest.id, bidRequest.imp).build

    builtBidRequest shouldBe bidRequest
  }

  it should "build BidRequest correctly" in {
    val bidRequest = BidRequest(
      "req",
      imp,
      Some(site),
      Some(app),
      Some(device),
      Some(user),
      1,
      3,
      Some(10),
      Some(Seq("b1", "b2")),
      1,
      Some(Seq("c1", "c2")),
      Some(Seq("IAB3-4")),
      Some(Seq("block1", "block2")),
      Some(regs),
      Some("ext"))

    var builder = BidRequestBuilder(bidRequest.id, bidRequest.imp)
    bidRequest.site.foreach(site => builder = builder.withSite(site))
    bidRequest.app.foreach(app => builder = builder.withApp(app))
    bidRequest.device.foreach(device => builder = builder.withDevice(device))
    bidRequest.user.foreach(user => builder = builder.withUser(user))
    bidRequest.tmax.foreach(tmax => builder = builder.withTmax(tmax))
    bidRequest.wseat.foreach(wseat => builder = builder.withWseat(wseat))
    bidRequest.cur.foreach(cur => builder = builder.withCur(cur))
    bidRequest.bcat.foreach(bcat => builder = builder.withBcat(bcat))
    bidRequest.badv.foreach(badv => builder = builder.withBadv(badv))
    bidRequest.regs.foreach(regs => builder = builder.withRegs(regs))
    bidRequest.ext.foreach(ext => builder = builder.withExt(ext))
    builder = builder
      .withTest(bidRequest.test)
      .withAt(bidRequest.at)
      .withAllImps(bidRequest.allImps)

    val builtBidRequest = builder.build

    builtBidRequest shouldBe bidRequest
  }
}
