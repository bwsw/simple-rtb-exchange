package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Banner
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class BannerBuilderTest extends FlatSpec with Matchers{

  "BannerBuilder" should "build Banner correctly" in {
    val banner = Banner(Some(1), Some(2), Some(3), Some(4), Some(5), Some(6),
      Some("id"), Some(Seq(7)), Some(Seq(8)), Some(9), Some(Seq("mime")),
      Some(10), Some(Seq(11)), Some(Seq(12)), Some("string"))

    val buildedBanner = BannerBuilder()
      .withW(1)
      .withH(2)
      .withWmax(3)
      .withHmax(4)
      .withWmin(5)
      .withHmin(6)
      .withId("id")
      .withBtype(Seq(7))
      .withBattr(Seq(8))
      .withPos(9)
      .withMimes(Seq("mime"))
      .withTopframe(10)
      .withExpdir(Seq(11))
      .withApi(Seq(12))
      .withExt("string")
      .build

    buildedBanner shouldBe banner
  }

}