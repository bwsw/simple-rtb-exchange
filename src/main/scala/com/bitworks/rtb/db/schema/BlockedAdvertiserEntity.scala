package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database blocked advertiser entity.
  *
  * @author Egor Ilchenko
  */
case class BlockedAdvertiserEntity(
    ID: Int,
    publisherId: Int,
    domain: String) extends BaseEntity
