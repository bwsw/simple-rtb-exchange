package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Title

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Title Title]].
  *
  * @param len value of len in [[com.bitworks.rtb.model.request.native.Title Title]] object
  * @author Pavel Tomskikh
  */
class TitleBuilder private(len: Int) {
  private var ext: Option[Any] = None

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Title(len, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Title Title]].
  *
  * @author Pavel Tomskikh
  */
object TitleBuilder {
  def apply(len: Int) = new TitleBuilder(len)
}
