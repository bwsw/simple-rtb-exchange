package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.Data

/**
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Builder for [[com.bitworks.rtb.model.request.native_adv.Data]].
  * @param type is required for [[com.bitworks.rtb.model.request.native_adv.Data]]
  */
class DataBuilder private (`type`: Int) {
  private var len: Option[Int] = None
  private var ext: Option[Any] = None

  def withLen(i: Int) = { len = Some(i); this }
  def withExt(a: Any) = { ext = Some(a); this }

  def build = Data(`type`, len, ext)
}

object DataBuilder {
  def apply(`type`: Int) = new DataBuilder(`type`)
}
