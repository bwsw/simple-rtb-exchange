package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Imp
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
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
        .withDisplaymanager("displaymanager")
        .withDisplaymanagerver("displaymanagerver")
        .withInstl(1)
        .withTagid("tagid")
        .withBidfloor(BigDecimal("2"))
        .withBidfloorcur("EUR")
        .withSecure(2)
        .withIframebuster(Seq("iframebuster"))
        .withPmp(PmpBuilder().build)
        .withExt("ext")
        .build

    buildedImp shouldBe imp
  }

}