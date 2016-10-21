package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Video
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.Video]]
  *
  * @author Egor Ilchenko
  *
  */
class VideoBuilderTest extends FlatSpec with Matchers{

  "VideoBuilder" should "build Video correctly" in {
    val video = Video(Seq("mime"), Some(1), Some(2), Some(3), Some(Seq(4)), Some(5),
      Some(6), Some(7), Some(8), Some(9), Some(Seq(10)), Some(11), Some(12), Some(13),
      14, Some(Seq(15)), Some(Seq(16)), Some(17), Some(Seq.empty), Some(Seq(18)),
      Some(Seq(19)), Some("string"))

    val buildedVideo = VideoBuilder(Seq("mime"))
      .withMinDuration(1)
      .withMaxDuration(2)
      .withProtocol(3)
      .withProtocols(Seq(4))
      .withW(5)
      .withH(6)
      .withStartDelay(7)
      .withLinearity(8)
      .withSequence(9)
      .withBattr(Seq(10))
      .withMaxExtended(11)
      .withMinBitrate(12)
      .withMaxBitrate(13)
      .withBoxingAllowed(14)
      .withPlaybackMethod(Seq(15))
      .withDelivery(Seq(16))
      .withPos(17)
      .withCompanionAd(Seq.empty)
      .withApi(Seq(18))
      .withCompanionType(Seq(19))
      .withExt("string")
      .build

    buildedVideo shouldBe video
  }

  it should "build Video with default values" in {
    val video = Video(Seq("mime"), None, None, None, None, None, None, None, None, None, None,
      None, None, None, 1, None, None, None, None, None, None, None)

    val buildedVideo = VideoBuilder(Seq("mime")).build

    buildedVideo shouldBe video
  }

}