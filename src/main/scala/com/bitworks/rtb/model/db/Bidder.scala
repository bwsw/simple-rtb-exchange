package com.bitworks.rtb.model.db

/**
  * An entity that competes in real-time auctions to acquire impressions.
  *
  * @param ID       database ID
  * @param name     name of the bidder
  * @param endpoint domain of the bidder
  * @author Egor Ilchenko
  */
case class Bidder(
    ID: Int,
    name: String,
    endpoint: String) extends BaseEntity
