package com.bitworks.rtb.model.db

/**
  * Mediation partner, SDK technology, or player responsible for rendering ad.
  *
  * @param id   database ID
  * @param name manager name
  * @param ver  manager version
  * @author Egor Ilchenko
  */
case class DisplayManager(
    id: Int,
    name: String,
    ver: String) extends BaseEntity
