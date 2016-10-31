package com.bitworks.rtb.model.db

/**
  * Details about website calling for the impression.
  *
  * @param ID              database id
  * @param name            name of the site
  * @param domain          domain of the site
  * @param iabCategories   IAB content categories of the site
  * @param privacyPolicy   is has a privacy policy
  * @param keyword         comma separated list of keywords about the site
  * @param displayManagers display managers associated with the site
  * @param publisher       publisher associated with the site
  * @author Egor Ilchenko
  */
case class Site(
    ID: Int,
    name: String,
    domain: String,
    iabCategories: Seq[String],
    privacyPolicy: Boolean,
    keyword: String,
    displayManagers: Seq[DisplayManager],
    publisher: Publisher) extends BaseEntity
