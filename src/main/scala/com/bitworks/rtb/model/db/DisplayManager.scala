package com.bitworks.rtb.model.db

/**
  * Display manager.
  *
  * @author Egor Ilchenko
  */
case class DisplayManager(
    ID: Int,
    name: String,
    ver: String,
    sites: Seq[String]) extends BaseEntity
