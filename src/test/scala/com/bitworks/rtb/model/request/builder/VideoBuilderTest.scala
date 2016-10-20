package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Video
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class VideoBuilderTest extends FlatSpec with Matchers{

  "VideoBuilder" should "build Video correctly" in {
    val video = Video(Seq("mime"), Some(1), Some(2), Some(3), Some(Seq(4)), Some(5),
      Some(6), Some(7), Some(8), Some(9), Some(Seq(10)), Some(11), Some(12), Some(13),
      Some(14), Some(Seq(15)), Some(Seq(16)), Some(17), Some(Seq.empty), Some(Seq(18)),
      Some(Seq(19)), Some("string"))

    val buildedVideo = VideoBuilder(Seq("mime"))
      .withMinduration(1)
      .withMaxduration(2)
      .withProtocol(3)
      .withProtocols(Seq(4))
      .withW(5)
      .withH(6)
      .withStartdelay(7)
      .withLinearity(8)
      .withSequence(9)
      .withBattr(Seq(10))
      .withMaxextended(11)
      .withMinbitrate(12)
      .withMaxbitrate(13)
      .withBoxingallowed(14)
      .withPlaybackmethod(Seq(15))
      .withDelivery(Seq(16))
      .withPos(17)
      .withCompanionad(Seq.empty)
      .withApi(Seq(18))
      .withCompaniontype(Seq(19))
      .withExt("string")
      .build

    buildedVideo shouldBe video
  }

}