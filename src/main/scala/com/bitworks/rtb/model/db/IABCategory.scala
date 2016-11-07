package com.bitworks.rtb.model.db

import com.bitworks.rtb.service.dao.schema.IABCategoryEntity

/**
  * Content category.
  *
  * @param id     database ID
  * @param iabID  category IAB ID
  * @param name   category description
  * @param parentId parent category ID
  * @author Egor Ilchenko
  */
case class IABCategory(
    id: Int,
    iabID: String,
    name: String,
    parentId: Option[Int]) extends BaseEntity
