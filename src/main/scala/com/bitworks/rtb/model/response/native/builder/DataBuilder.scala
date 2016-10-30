package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Data

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Data Data]].
  *
  * @author Egor Ilchenko
  */
class DataBuilder(value: String) {
  private var label: Option[String] = None
  private var ext: Option[Any] = None

  def withLabel(s: String) = {
    label = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Data */
  def build = Data(label, value, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.native.Data Data]].
  *
  * @author Egor Ilchenko
  */
object DataBuilder {
  def apply(value: String) = new DataBuilder(value)
}
