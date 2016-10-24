package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Video

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Video Video]].
  *
  * @author Egor Ilchenko
  */
class VideoBuilder(vasttag: String) {
    /** Returns Video */
  def build = Video(vasttag)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Video Video]].
  *
  * @author Egor Ilchenko
  */
object VideoBuilder {
  def apply(vasttag: String) = new VideoBuilder(vasttag)
}
