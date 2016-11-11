package com.bitworks.rtb.model.db

/**
  * App or site status.
  *
  * @author Egor Ilchenko
  */
object Status extends Enumeration {
  val inactive = Value(0)
  val active = Value(1)
}
