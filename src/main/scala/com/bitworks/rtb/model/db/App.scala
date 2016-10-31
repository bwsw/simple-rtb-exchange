package com.bitworks.rtb.model.db

/**
  * Details of the application calling for the impression.
  *
  * @param ID            database id
  * @param name          name of the site
  * @param domain        domain of the site
  * @param iabCategories IAB content categories of the site
  * @param privacyPolicy is has a privacy policy
  * @param bundle        application bundle or package name
  * @param version       application version
  * @param storeURL      application store URL for an installed app
  * @param keyword       comma separated list of keywords about the site
  * @param publisher     publisher asscociated with the app
  * @author Egor Ilchenko
  */
case class App(
    ID: Int,
    name: String,
    domain: String,
    iabCategories: Seq[String],
    privacyPolicy: Boolean,
    bundle: String,
    storeURL: String,
    version: String,
    keyword: String,
    publisher: Publisher) extends BaseEntity
