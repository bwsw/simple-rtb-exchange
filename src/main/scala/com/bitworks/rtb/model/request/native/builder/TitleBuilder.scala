package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Title

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Title]].
  *
  * @param len is required for [[com.bitworks.rtb.model.request.native.Title]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class TitleBuilder private (len: Int) {
  private var ext: Option[Any] = None

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Title(len, ext)
}

object TitleBuilder {
  def apply(len: Int) = new TitleBuilder(len)
}