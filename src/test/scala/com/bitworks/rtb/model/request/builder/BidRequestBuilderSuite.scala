package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.BidRequest
import org.scalatest.FunSuite

/** Test for [[com.bitworks.rtb.model.request.builder.BidRequestBuilder]]
  *
  * Created on: 10/20/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class BidRequestBuilderSuite extends FunSuite {

  val imp = Seq(ImpBuilder("imp").build)
  val site = SiteBuilder().build
  val app = AppBuilder().build
  val device = DeviceBuilder().build
  val user = UserBuilder().build
  val regs = RegsBuilder().build

  test("Test building BidRequest without optional arguments") {
    assert(
      BidRequestBuilder("req", imp).build ===
          BidRequest(
            "req",
            imp,
            None,
            None,
            None,
            None,
            Some(0),
            Some(2),
            None,
            None,
            Some(0),
            None,
            None,
            None,
            None,
            None))
  }

  test("Test building BidRequest with optional arguments") {
    assert(
      BidRequestBuilder("req", imp)
          .withSite(site)
          .withApp(app)
          .withDevice(device)
          .withUser(user)
          .withTest(1)
          .withAt(1)
          .withTMax(500)
          .withWSeat(Seq("buyer1", "buyer2"))
          .withAllImps(1)
          .withCur(Seq("c1", "c2"))
          .withBCat(Seq("IAB1-3", "IAB5-7"))
          .withBadv(Seq("IAB2-2", "IAB2-4"))
          .withRegs(regs)
          .withExt("ext")
          .build ===
          BidRequest(
            "req",
            imp,
            Some(site),
            Some(app),
            Some(device),
            Some(user),
            Some(1),
            Some(1),
            Some(500),
            Some(Seq("buyer1", "buyer2")),
            Some(1),
            Some(Seq("c1", "c2")),
            Some(Seq("IAB1-3", "IAB5-7")),
            Some(Seq("IAB2-2", "IAB2-4")),
            Some(regs),
            Some("ext")))
  }

}
