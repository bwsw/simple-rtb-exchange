package com.bitworks.rtb.service.dao.schema

/**
  * Database entity meta information.
  *
  * @author Egor Ilchenko
  */
trait EntityMetaInfo {
  val tsversion: Long
  val deleted: Boolean
}
