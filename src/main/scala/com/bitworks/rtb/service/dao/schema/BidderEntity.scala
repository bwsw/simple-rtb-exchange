package com.bitworks.rtb.service.dao.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database bidder entity.
  *
  * @author Egor Ilchenko
  */
case class BidderEntity(
    id: Int,
    tsversion: Long,
    deleted: Boolean,
    name: String,
    endpoint: String) extends BaseEntity with EntityMetaInfo

