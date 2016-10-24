package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Video
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.VideoBuilder VideoBuilder]].
  *
  * @author Egor Ilchenko
  */
class VideoBuilderTest extends FlatSpec with Matchers {

  "VideoBuilder" should "build Video correctly" in {
    val video = Video(
      Seq("mime"),
      Some(1),
      Some(2),
      Some(3),
      Some(Seq(4)),
      Some(5),
      Some(6),
      Some(7),
      Some(8),
      Some(9),
      Some(Seq(10)),
      Some(11),
      Some(12),
      Some(13),
      14,
      Some(Seq(15)),
      Some(Seq(16)),
      Some(17),
      Some(Seq.empty),
      Some(Seq(18)),
      Some(Seq(19)),
      Some("string"))

    val builder = VideoBuilder(video.mimes)
      .withBoxingAllowed(video.boxingAllowed)
    video.minDuration.foreach(minDuration => builder.withMinDuration(minDuration))
    video.maxDuration.foreach(maxDuration => builder.withMaxDuration(maxDuration))
    video.protocol.foreach(protocol => builder.withProtocol(protocol))
    video.protocols.foreach(protocols => builder.withProtocols(protocols))
    video.w.foreach(w => builder.withW(w))
    video.h.foreach(h => builder.withH(h))
    video.startDelay.foreach(startDelay => builder.withStartDelay(startDelay))
    video.linearity.foreach(linearity => builder.withLinearity(linearity))
    video.sequence.foreach(sequence => builder.withSequence(sequence))
    video.battr.foreach(battr => builder.withBattr(battr))
    video.maxExtended.foreach(maxExtended => builder.withMaxExtended(maxExtended))
    video.minBitrate.foreach(minBitrate => builder.withMinBitrate(minBitrate))
    video.maxBitrate.foreach(maxBitrate => builder.withMaxBitrate(maxBitrate))
    video.playbackMethod.foreach(playbackMethod => builder.withPlaybackMethod(playbackMethod))
    video.delivery.foreach(delivery => builder.withDelivery(delivery))
    video.pos.foreach(pos => builder.withPos(pos))
    video.companionAd.foreach(companionAd => builder.withCompanionAd(companionAd))
    video.api.foreach(api => builder.withApi(api))
    video.companionType.foreach(companionType => builder.withCompanionType(companionType))
    video.ext.foreach(ext => builder.withExt(ext))

    val builtVideo = builder.build

    builtVideo shouldBe video
  }

  it should "build Video with default values correctly" in {
    val video = Video(
      Seq("mime"),
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
      1,
      None,
      None,
      None,
      None,
      None,
      None,
      None)

    val builtVideo = VideoBuilder(Seq("mime")).build

    builtVideo shouldBe video
  }

}
