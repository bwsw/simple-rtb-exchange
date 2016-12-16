package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.Imp
import com.bitworks.rtb.model.request.builder.{BannerBuilder, NativeBuilder, VideoBuilder}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.builder.ImpBuilder ImpBuilder]].
  *
  * @author Egor Ilchenko
  */
class ImpBuilderTest extends FlatSpec with Matchers {

  "ImpBuilder" should "build Imp correctly" in {
    val imp = Imp(
      "id",
      Some(BannerBuilder().build),
      Some(VideoBuilder(Seq("mime")).build),
      Some(NativeBuilder("request").build))

    val builder = ImpBuilder(imp.id)
    imp.banner.foreach(banner => builder.withBanner(banner))
    imp.video.foreach(video => builder.withVideo(video))
    imp.native.foreach(native => builder.withNative(native))

    val builtImp = builder.build

    builtImp shouldBe imp
  }

  it should "build Imp with default values correctly" in {
    val imp = Imp(
      "id",
      None,
      None,
      None)

    val builtImp = ImpBuilder(imp.id).build

    builtImp shouldBe imp
  }

}
