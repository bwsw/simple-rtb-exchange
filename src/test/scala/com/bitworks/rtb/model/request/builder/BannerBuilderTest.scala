package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Banner
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.BannerBuilder BannerBuilder]].
  *
  * @author Egor Ilchenko
  */
class BannerBuilderTest extends FlatSpec with Matchers {

  "BannerBuilder" should "build Banner correctly" in {
    val banner = Banner(
      Some(1),
      Some(2),
      Some(3),
      Some(4),
      Some(5),
      Some(6),
      Some("id"),
      Some(Seq(7)),
      Some(Seq(8)),
      Some(9),
      Some(Seq("mime")),
      Some(10),
      Some(Seq(11)),
      Some(Seq(12)),
      Some("string"))

    val builder = BannerBuilder()
    banner.w.foreach(w => builder.withW(w))
    banner.h.foreach(h => builder.withH(h))
    banner.wmax.foreach(wmax => builder.withWmax(wmax))
    banner.hmax.foreach(hmax => builder.withHmax(hmax))
    banner.wmin.foreach(wmin => builder.withWmin(wmin))
    banner.hmin.foreach(hmin => builder.withHmin(hmin))
    banner.id.foreach(id => builder.withId(id))
    banner.btype.foreach(btype => builder.withBtype(btype))
    banner.battr.foreach(battr => builder.withBattr(battr))
    banner.pos.foreach(pos => builder.withPos(pos))
    banner.mimes.foreach(mimes => builder.withMimes(mimes))
    banner.topFrame.foreach(topFrame => builder.withTopFrame(topFrame))
    banner.expdir.foreach(expdir => builder.withExpdir(expdir))
    banner.api.foreach(api => builder.withApi(api))
    banner.ext.foreach(ext => builder.withExt(ext))

    val builtBanner = builder.build

    builtBanner shouldBe banner
  }

  it should "build Banner with default values correctly" in {
    val banner = Banner(
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

    val builtBanner = BannerBuilder().build

    builtBanner shouldBe banner
  }

}
