package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Data

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Data Data]].
  *
  * @param type value of type in [[com.bitworks.rtb.model.request.native.Data Data]] object.
  * @author Pavel Tomskikh
  */
class DataBuilder private(`type`: Int) {
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

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Data Data]].
  *
  * @author Pavel Tomskikh
  */
object DataBuilder {
  def apply(`type`: Int) = new DataBuilder(`type`)
}
