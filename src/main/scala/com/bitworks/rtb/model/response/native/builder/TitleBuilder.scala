package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Title

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Title Title]].
  *
  * @author Egor Ilchenko
  */
class TitleBuilder(text: String) {
  private var ext: Option[Any] = None

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Title */
  def build = Title(text, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Title Title]].
  *
  * @author Egor Ilchenko
  */
object TitleBuilder {
  def apply(text: String) = new TitleBuilder(text)
}
