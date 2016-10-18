package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Regs

/** Builder for Regs model  */
object RegsBuilder {
  protected class RegsBuilder{
    var coppa: Int = 0
    var ext: Option[Any] = None

    def withCoppa(i: Int) = { coppa = i; this }
    def withExt(a: Any) = { ext = Some(a); this }

    /** Returns Regs */
    def build = Regs(coppa, ext)
  }
  /** Returns builder for Regs */
  def builder = new RegsBuilder
}
