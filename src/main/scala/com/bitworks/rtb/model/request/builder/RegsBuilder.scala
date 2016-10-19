package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Regs

/** Builder for Regs model
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class RegsBuilder private {
  private var coppa: Int = 0
  private var ext: Option[Any] = None

  def withCoppa(i: Int) = {
    coppa = i
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Regs */
  def build = Regs(coppa, ext)
}

/** Builder for Regs model  */
object RegsBuilder {
  def apply() = new RegsBuilder()
}
