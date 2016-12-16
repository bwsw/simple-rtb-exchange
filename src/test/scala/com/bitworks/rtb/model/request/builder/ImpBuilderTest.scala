package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Imp
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.ImpBuilder ImpBuilder]].
  *
  * @author Egor Ilchenko
  */
class ImpBuilderTest extends FlatSpec with Matchers {

  "ImpBuilder" should "build Imp correctly" in {
    val imp = Imp(
      "id",
      Some(BannerBuilder().build),
      Some(VideoBuilder(Seq("mime")).build),
      Some(NativeBuilder("request").build),
      Some("displaymanager"),
      Some("displaymanagerver"),
      1,
      Some("tagid"),
      BigDecimal("2"),
      "EUR",
      Some(2),
      Some(Seq("iframebuster")),
      Some(PmpBuilder().build),
      Some("ext"))

    var builder = ImpBuilder(imp.id)
      .withInstl(imp.instl)
      .withBidFloor(imp.bidFloor)
      .withBidFloorCur(imp.bidFloorCur)
    imp.banner.foreach(banner => builder = builder.withBanner(banner))
    imp.video.foreach(video => builder = builder.withVideo(video))
    imp.native.foreach(native => builder = builder.withNative(native))
    imp.displayManager.foreach(displayManager => builder = builder.withDisplayManager(displayManager))
    imp.displayManagerVer.foreach(displayManagerVer => builder = builder.withDisplayManagerVer(displayManagerVer))
    imp.tagId.foreach(tagId => builder = builder.withTagId(tagId))
    imp.secure.foreach(secure => builder = builder.withSecure(secure))
    imp.iframeBuster.foreach(iframeBuster => builder = builder.withIframeBuster(iframeBuster))
    imp.pmp.foreach(pmp => builder = builder.withPmp(pmp))
    imp.ext.foreach(ext => builder = builder.withExt(ext))

    val builtImp = builder.build

    builtImp shouldBe imp
  }

  it should "build Imp with default values correctly" in {
    val imp = Imp(
      "id",
      None,
      None,
      None,
      None,
      None,
      ImpBuilder.Instl,
      None,
      ImpBuilder.BidFloor,
      ImpBuilder.BidFloorCur,
      None,
      None,
      None,
      None)

    val builtImp = ImpBuilder(imp.id).build

    builtImp shouldBe imp

  }

}
