package com.bitworks.rtb.service.dao.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database publisher blocked advertiser entity.
  *
  * @author Egor Ilchenko
  */
case class PublisherBlockedAdvertiserEntity(
    id: Int,
    publisherId: Int,
    domain: String) extends BaseEntity
