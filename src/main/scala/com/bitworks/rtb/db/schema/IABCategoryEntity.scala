package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database IAB category entity.
  *
  * @author Egor Ilchenko
  */
case class IABCategoryEntity(
    ID: Int,
    tsVersion: Long,
    deleted: Boolean,
    iabID: String,
    name: String,
    parentId: Option[Int]) extends BaseEntity with EntityMetaInfo
