package com.bitworks.rtb.service.dao.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database display manager entity.
  *
  * @author Egor Ilchenko
  */
case class DisplayManagerEntity(
    id: Int,
    tsversion: Long,
    deleted: Boolean,
    name: String,
    ver: String) extends BaseEntity with EntityMetaInfo
