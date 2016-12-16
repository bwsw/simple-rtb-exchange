package com.bitworks.rtb.service.dao.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database publisher entity.
  *
  * @author Egor Ilchenko
  */
case class PublisherEntity(
    id: Int,
    tsversion: Long,
    deleted: Boolean,
    name: String,
    domain: String) extends BaseEntity with EntityMetaInfo
