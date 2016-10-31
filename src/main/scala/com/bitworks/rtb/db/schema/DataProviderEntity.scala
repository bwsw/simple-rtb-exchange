package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database data provider entity.
  *
  * @author Egor Ilchenko
  */
case class DataProviderEntity(
    ID: Int,
    tsVersion: Long,
    deleted: Int,
    name: String) extends BaseEntity with EntityMetaInfo
