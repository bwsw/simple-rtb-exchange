package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Video
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.VideoBuilder]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class VideoBuilderTest extends FlatSpec with Matchers {

  "VideoBuilder" should "build Video with default parameters correctly" in {
    val video = Video(None, None, None, None, None)
    val buildedVideo = VideoBuilder().build

    buildedVideo shouldBe video
  }

  it should "build Video with optional parameters correctly" in {
    val video = Video(
      Some(Seq("image/jpg")),
      Some(10),
      Some(30),
      Some(Seq(1, 4)),
      Some("ext"))
    val buildedVideo = VideoBuilder()
        .withMimes(Seq("image/jpg"))
        .withMinDuration(10)
        .withMaxDuration(30)
        .withProtocols(Seq(1, 4))
        .withExt("ext")
        .build

    buildedVideo shouldBe video
  }
}
