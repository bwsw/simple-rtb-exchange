package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Video
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.native.builder.VideoBuilder VideoBuilder]].
  *
  * @author Egor Ilchenko
  */
class VideoBuilderTest extends FlatSpec with Matchers {

  "VideoBuilder" should "build Video correctly" in {
    val video = Video("vasttag")

    val builtVideo = VideoBuilder(video.vasttag).build

    builtVideo shouldBe video
  }
  
}
