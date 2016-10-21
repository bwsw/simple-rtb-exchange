package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.Video
import org.scalatest.FunSuite

/**
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Test for [[com.bitworks.rtb.model.request.native_adv.builder.VideoBuilder]]
  */
class VideoBuilderSuite extends FunSuite {
  test("Test building Video without optional arguments") {
    assert(
      VideoBuilder().build === Video(None, None, None, None, None))
  }

  test("Test building Video with optional arguments") {
    assert(
      VideoBuilder()
        .withMimes(Seq("image/jpg"))
        .withMinDuration(10)
        .withMaxDuration(30)
        .withProtocols(Seq(1, 4))
        .withExt("ext")
        .build ===
        Video(
          Some(Seq("image/jpg")),
          Some(10),
          Some(30),
          Some(Seq(1, 4)),
          Some("ext")))
  }
}
