package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.request.builder.{DeviceBuilder, RegsBuilder}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder AdRequestBuilder]].
  *
  * @author Egor Ilchenko
  */
class AdRequestBuilderTest extends FlatSpec with Matchers {

  "AdRequestBuilder" should "build AdRequest correctly" in {
    val adRequest = AdRequest(
      Seq(ImpBuilder("id").build),
      Some(SiteBuilder(1001).build),
      Some(AppBuilder(1002).build),
      Some(DeviceBuilder().build),
      Some(UserBuilder().build),
      1,
      Some(2),
      Some(RegsBuilder().build))

    val builder = AdRequestBuilder(adRequest.imp).withTest(adRequest.test)
    adRequest.site.foreach(site => builder.withSite(site))
    adRequest.app.foreach(app => builder.withApp(app))
    adRequest.device.foreach(device => builder.withDevice(device))
    adRequest.user.foreach(user => builder.withUser(user))
    adRequest.tmax.foreach(tmax => builder.withTmax(tmax))
    adRequest.regs.foreach(regs => builder.withRegs(regs))

    val builtAdRequest = builder.build

    builtAdRequest shouldBe adRequest
  }

  it should "build AdRequest with default values correctly" in {
    val adRequest = AdRequest(
      Seq(ImpBuilder("id").build),
      None,
      None,
      None,
      None,
      AdRequestBuilder.Test,
      None,
      None)

    val builtAdRequest = AdRequestBuilder(adRequest.imp).build

    builtAdRequest shouldBe adRequest
  }

}
