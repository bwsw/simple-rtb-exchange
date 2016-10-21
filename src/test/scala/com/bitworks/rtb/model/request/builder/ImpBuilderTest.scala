package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Imp
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.Imp]]
  *
  * @author Egor Ilchenko
  *
  */
class ImpBuilderTest extends FlatSpec with Matchers{

  "ImpBuilder" should "build Imp correctly" in {
    val imp = Imp("id", Some(BannerBuilder().build), Some(VideoBuilder(Seq("mime")).build),
      Some(NativeBuilder("request").build), Some("displaymanager"), Some("displaymanagerver"),
      1, Some("tagid"), BigDecimal("2"), "EUR", Some(2), Some(Seq("iframebuster")),  Some
      (PmpBuilder().build), Some("ext"))

    val buildedImp = ImpBuilder("id")
        .withBanner(BannerBuilder().build)
        .withVideo(VideoBuilder(Seq("mime")).build)
        .withNative(NativeBuilder("request").build)
        .withDisplayManager("displaymanager")
        .withDisplayManagerVer("displaymanagerver")
        .withInstl(1)
        .withTagId("tagid")
        .withBidFloor(BigDecimal("2"))
        .withBidFloorCur("EUR")
        .withSecure(2)
        .withIframeBuster(Seq("iframebuster"))
        .withPmp(PmpBuilder().build)
        .withExt("ext")
        .build

    buildedImp shouldBe imp
  }

  it should "build Imp with default values" in {
    val imp = Imp("id", None, None, None, None, None, 0, None,
        BigDecimal("0"), "USD", None, None, None, None)

    val buildedImp = ImpBuilder("id").build

    buildedImp shouldBe imp

  }

}