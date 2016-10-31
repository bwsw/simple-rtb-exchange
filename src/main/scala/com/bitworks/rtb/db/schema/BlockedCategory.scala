package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database blocked category entity.
  *
  * @author Egor Ilchenko
  */
case class BlockedCategory(
    ID: Int,
    publisherId: Int,
    iabCategoryId: Int) extends BaseEntity
