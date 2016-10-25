package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.AdRequestImp
import com.bitworks.rtb.model.request.builder.{BannerBuilder, NativeBuilder, VideoBuilder}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.AdRequestImp AdRequestImp]].
  *
  * @author Egor Ilchenko
  */
class AdRequestImpBuilderTest extends FlatSpec with Matchers {

  "AdRequestImpBuilder" should "build AdRequestImp correctly" in {
    val adRequestImp = AdRequestImp(
      "id",
      Some(BannerBuilder().build),
      Some(VideoBuilder(Seq("mime")).build),
      Some(NativeBuilder("request").build))

    val builder = AdRequestImpBuilder(adRequestImp.id)
    adRequestImp.banner.foreach(banner => builder.withBanner(banner))
    adRequestImp.video.foreach(video => builder.withVideo(video))
    adRequestImp.native.foreach(native => builder.withNative(native))

    val builtAdRequestImp = builder.build

    builtAdRequestImp shouldBe adRequestImp
  }

  it should "build AdRequestImp with default values correctly" in {
    val adRequestImp = AdRequestImp(
      "id",
      None,
      None,
      None)

    val builtAdRequestImp = AdRequestImpBuilder(adRequestImp.id).build

    builtAdRequestImp shouldBe adRequestImp
  }

}
