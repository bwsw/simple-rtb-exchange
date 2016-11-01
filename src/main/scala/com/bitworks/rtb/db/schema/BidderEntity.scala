package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database bidder entity.
  *
  * @author Egor Ilchenko
  */
case class BidderEntity(
    ID: Int,
    tsVersion: Long,
    deleted: Boolean,
    name: String,
    endpoint: String) extends BaseEntity with EntityMetaInfo

