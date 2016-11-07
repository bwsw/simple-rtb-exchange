package com.bitworks.rtb.model.db

/**
  * An entity that competes in real-time auctions to acquire impressions.
  *
  * @param id       database ID
  * @param name     name of the bidder
  * @param endpoint domain of the bidder
  * @author Egor Ilchenko
  */
case class Bidder(
    id: Int,
    name: String,
    endpoint: String) extends BaseEntity
