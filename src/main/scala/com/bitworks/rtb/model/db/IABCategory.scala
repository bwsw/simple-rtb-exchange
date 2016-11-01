package com.bitworks.rtb.model.db

/**
  * Content category.
  *
  * @param ID     database ID
  * @param iabID  category IAB ID
  * @param name   category description
  * @param parent parent category
  * @author Egor Ilchenko
  */
case class IABCategory(
    ID: Int,
    iabID: String,
    name: String,
    parent: Option[IABCategory]) extends BaseEntity
