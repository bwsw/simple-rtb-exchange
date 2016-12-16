package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Video
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.VideoBuilder VideoBuilder]].
  *
  * @author Pavel Tomskikh
  */
class VideoBuilderTest extends FlatSpec with Matchers {

  "VideoBuilder" should "build Video with default values correctly" in {
    val video = Video(Seq("image/jpg"), 10, 30, Seq(1, 4), None)

    var builder = VideoBuilder(video.mimes, video.minDuration, video.maxDuration, video.protocols)
    val builtVideo = builder.build

    builtVideo shouldBe video
  }

  it should "build Video correctly" in {
    val video = Video(Seq("image/jpg"), 10, 30, Seq(1, 4), Some("ext"))

    var builder = VideoBuilder(video.mimes, video.minDuration, video.maxDuration, video.protocols)
    video.ext.foreach(ext => builder = builder.withExt(ext))

    val builtVideo = builder.build

    builtVideo shouldBe video
  }
}
