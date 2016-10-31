package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database publisher entity.
  *
  * @author Egor Ilchenko
  */
case class PublisherEntity(
    ID: Int,
    tsVersion: Long,
    deleted: Int,
    name: String,
    domain: String) extends BaseEntity with EntityMetaInfo
