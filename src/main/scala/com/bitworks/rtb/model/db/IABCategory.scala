package com.bitworks.rtb.model.db

/**
  * Content category.
  *
  * @param id       database ID
  * @param iabId    category IAB ID
  * @param name     category description
  * @param parentId parent category ID
  * @author Egor Ilchenko
  */
case class IABCategory(
    id: Int,
    iabId: String,
    name: String,
    parentId: Option[Int]) extends BaseEntity
