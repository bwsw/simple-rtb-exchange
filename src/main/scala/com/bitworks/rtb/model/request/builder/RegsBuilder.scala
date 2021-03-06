package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Regs

/**
  * Builder for [[com.bitworks.rtb.model.request.Regs Regs]].
  *
  * @author Egor Ilchenko
  */
class RegsBuilder private {
  private var coppa: Option[Int] = None
  private var ext: Option[Any] = None

  def withCoppa(i: Int) = {
    coppa = Some(i)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Regs */
  def build = Regs(coppa, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.Regs Regs]].
  *
  * @author Egor Ilchenko
  */
object RegsBuilder {
  def apply() = new RegsBuilder()
}
