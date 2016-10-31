package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database site display manager entity.
  *
  * @author Egor Ilchenko
  */
case class SiteDisplayManagerEntity(
    ID: Int,
    siteId: Int,
    displayManagerId: Int) extends BaseEntity
