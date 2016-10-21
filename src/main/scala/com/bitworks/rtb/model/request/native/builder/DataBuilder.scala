package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Data

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Data]].
  * 
  * @param type is required for [[com.bitworks.rtb.model.request.native.Data]]
  * @author Pavel Tomskikh
  *
  */
class DataBuilder private (`type`: Int) {
  private var len: Option[Int] = None
  private var ext: Option[Any] = None

  def withLen(i: Int) = {
    len = Some(i)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Data(`type`, len, ext)
}

object DataBuilder {
  def apply(`type`: Int) = new DataBuilder(`type`)
}
