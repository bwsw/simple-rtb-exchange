package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.request.builder.{DeviceBuilder, RegsBuilder}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @author Egor Ilchenko
  */
class AdRequestBuilderTest extends FlatSpec with Matchers {

  "AdRequestBuilder" should "build AdRequest correctly" in {
    val adRequest = AdRequest(
      Seq(AdRequestImpBuilder("id").build),
      Some(SiteBuilder().build),
      Some(AppBuilder().build),
      DeviceBuilder().build,
      Some(UserBuilder().build),
      Some(1),
      Some(2),
      Some(RegsBuilder().build))

    val builder = AdRequestBuilder(adRequest.imp, adRequest.device)
    adRequest.site.foreach(site => builder.withSite(site))
    adRequest.app.foreach(app => builder.withApp(app))
    adRequest.user.foreach(user => builder.withUser(user))
    adRequest.test.foreach(test => builder.withTest(test))
    adRequest.tmax.foreach(tmax => builder.withTmax(tmax))
    adRequest.regs.foreach(regs => builder.withRegs(regs))

    val builtAdRequest = builder.build

    builtAdRequest shouldBe adRequest
  }

  it should "build AdRequest with default values correctly" in {
    val adRequest = AdRequest(
      Seq(AdRequestImpBuilder("id").build),
      None,
      None,
      DeviceBuilder().build,
      None,
      None,
      None,
      None)

    val builtAdRequest = AdRequestBuilder(adRequest.imp, adRequest.device).build

    builtAdRequest shouldBe adRequest
  }

}
