package com.bitworks.rtb.model.db

/**
  * Publisher of the media in which the ad will be displayed.
  *
  * @param id                   database ID
  * @param name                 publisher name
  * @param categoriesIds        Ids of IAB content categories that describe the publisher
  * @param domain               domain of the publisher
  * @param blockedDomains       block list of advertisers by their domains
  * @param blockedCategoriesIds ids of blocked advertiser categories
  * @author Egor Ilchenko
  */
case class Publisher(
    id: Int,
    name: String,
    categoriesIds: Seq[Int],
    domain: String,
    blockedDomains: Seq[String],
    blockedCategoriesIds: Seq[Int]) extends BaseEntity
