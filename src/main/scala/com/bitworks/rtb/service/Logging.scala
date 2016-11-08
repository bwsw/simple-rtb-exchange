package com.bitworks.rtb.service

import com.typesafe.scalalogging._

/**
  * Logging facade.
  *
  * @author Egor Ilchenko
  */
trait Logging extends LazyLogging{
  protected lazy val log = logger
}
