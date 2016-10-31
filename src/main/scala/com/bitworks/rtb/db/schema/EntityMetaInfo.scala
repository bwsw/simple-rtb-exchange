package com.bitworks.rtb.db.schema

/**
  * Database entity meta information.
  *
  * @author Egor Ilchenko
  */
trait EntityMetaInfo {
  val tsVersion: Long
  val deleted: Int
}
