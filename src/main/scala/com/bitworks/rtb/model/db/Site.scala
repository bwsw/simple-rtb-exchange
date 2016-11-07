package com.bitworks.rtb.model.db

/**
  * Details about website calling for the impression.
  *
  * @param id            database ID
  * @param name          name of the site
  * @param status        site status
  * @param privacyPolicy is has a privacy policy
  * @param test          is site in test mode
  * @param domain        domain of the site
  * @param keyword       comma separated list of keywords about the site
  * @param iabCategories IAB content categories of the site
  * @author Egor Ilchenko
  */
case class Site(
    id: Int,
    name: String,
    status: Status.Value,
    privacyPolicy: Int,
    test: Boolean,
    domain: String,
    keyword: Option[String],
    iabCategories: Seq[IABCategory]) extends BaseEntity
