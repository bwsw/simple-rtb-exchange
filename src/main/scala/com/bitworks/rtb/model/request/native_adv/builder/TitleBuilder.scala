package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.Title

/**
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Builder for [[com.bitworks.rtb.model.request.native_adv.Title]].
  * @param len is required for [[com.bitworks.rtb.model.request.native_adv.Title]]
  */
class TitleBuilder private (len: Int) {
  private var ext: Option[Any] = None

  def withExt(a: Any) = { ext = Some(a); this }

  def build = Title(len, ext)
}

object TitleBuilder {
  def apply(len: Int) = new TitleBuilder(len)
}