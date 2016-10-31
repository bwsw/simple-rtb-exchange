package com.bitworks.rtb.model.db

/**
  * Key-value pairs that convey specific units of data about the user.
  *
  * @param ID           database id
  * @param name         key
  * @param value        value
  * @param dataProvider associated DataProvider
  * @author Egor Ilchenko
  */
case class Segment(
    ID: Int,
    name: String,
    value: String,
    dataProvider: DataProvider) extends BaseEntity
