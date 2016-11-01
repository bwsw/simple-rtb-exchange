package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database display manager entity.
  *
  * @author Egor Ilchenko
  */
case class DisplayManagerEntity(
    ID: Int,
    tsVersion: Long,
    deleted: Boolean,
    name: String,
    ver: String) extends BaseEntity with EntityMetaInfo
