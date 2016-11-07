package com.bitworks.rtb.model.db

/**
  * Details of the application calling for the impression.
  *
  * @param id            database ID
  * @param name          name of the site
  * @param status        app status
  * @param privacyPolicy is has a privacy policy
  * @param test          is app in test mode
  * @param domain        domain of the site
  * @param keyword       comma separated list of keywords about the site
  * @param iabCategories IAB content categories of the site
  * @param bundle        application bundle or package name
  * @param storeURL      application store URL for an installed app
  * @param version       application version
  * @author Egor Ilchenko
  */
case class App(
    id: Int,
    name: String,
    status: Status.Value,
    privacyPolicy: Int,
    test: Boolean,
    domain: Option[String],
    keyword: Option[String],
    iabCategories: Seq[IABCategory],
    bundle: String,
    storeURL: String,
    version: String) extends BaseEntity
