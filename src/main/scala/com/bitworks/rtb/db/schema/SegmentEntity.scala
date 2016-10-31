package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database segment entity.
  *
  * @author Egor Ilchenko
  */
case class SegmentEntity(
    ID: Int,
    tsVersion: Long,
    deleted: Int,
    dataProviderId: Int,
    name: String,
    value: String) extends BaseEntity with EntityMetaInfo
