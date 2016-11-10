package com.bitworks.rtb.model.db

/**
  * Details about website calling for the impression.
  *
  * @param id               database ID
  * @param name             name of the site
  * @param publisherId      owner ID of the site
  * @param status           site status
  * @param privacyPolicy    privacy policy
  * @param test             indicates if the site is in test mode
  * @param domain           domain of the site
  * @param keyword          comma separated list of keywords about the site
  * @param iabCategoriesIds ids of IAB content categories of the site
  * @author Egor Ilchenko
  */
case class Site(
    id: Int,
    name: String,
    publisherId: Int,
    status: Status.Value,
    privacyPolicy: Int,
    test: Boolean,
    domain: String,
    keyword: Option[String],
    iabCategoriesIds: Seq[Int]) extends BaseEntity
