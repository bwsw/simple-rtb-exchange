package com.bitworks.rtb.model.db

/**
  * Publisher of the media in which the ad will be displayed.
  *
  * @param ID                database ID
  * @param name              publisher name
  * @param categories        IAB content categories that describe the publisher
  * @param domain            domain of the publisher
  * @param blockedDomains    block list of advertisers by their domains
  * @param blockedCategories blocked advertiser categories
  * @param sites             apps associated with the publisher
  * @param apps              apps associated with the publisher
  * @author Egor Ilchenko
  */
case class Publisher(
    ID: Int,
    name: String,
    categories: Seq[IABCategory],
    domain: String,
    blockedDomains: Seq[String],
    blockedCategories: Seq[IABCategory],
    sites: Seq[Site],
    apps: Seq[App]) extends BaseEntity
