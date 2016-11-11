package com.bitworks.rtb.service.dao.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database IAB category entity.
  *
  * @author Egor Ilchenko
  */
case class IABCategoryEntity(
    id: Int,
    tsversion: Long,
    deleted: Boolean,
    iabId: String,
    name: String,
    parentId: Option[Int]) extends BaseEntity with EntityMetaInfo
