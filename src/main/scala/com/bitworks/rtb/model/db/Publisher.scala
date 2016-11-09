package com.bitworks.rtb.model.db

/**
  * Publisher of the media in which the ad will be displayed.
  *
  * @param id                database ID
  * @param name              publisher name
  * @param categories        IAB content categories that describe the publisher
  * @param domain            domain of the publisher
  * @param blockedDomains    block list of advertisers by their domains
  * @param blockedCategories blocked advertiser categories
  * @author Egor Ilchenko
  */
case class Publisher(
    id: Int,
    name: String,
    categories: Seq[IABCategory],
    domain: String,
    blockedDomains: Seq[String],
    blockedCategories: Seq[IABCategory]) extends BaseEntity
