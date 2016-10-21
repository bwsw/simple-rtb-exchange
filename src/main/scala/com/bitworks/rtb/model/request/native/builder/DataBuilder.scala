package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.Data

/**
  * Builder for [[com.bitworks.rtb.model.request.native.Data]].
  * 
  * @param type is required for [[com.bitworks.rtb.model.request.native.Data]]
  *
  * Created on: 10/19/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
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
