package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Video

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Video Video]].
  *
  * @author Egor Ilchenko
  */
class VideoBuilder(vastTag: String) {
    /** Returns Video */
  def build = Video(vastTag)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Video Video]].
  *
  * @author Egor Ilchenko
  */
object VideoBuilder {
  def apply(vastTag: String) = new VideoBuilder(vastTag)
}
