package com.bitworks.rtb.model.db

/**
  * Details of the application calling for the impression.
  *
  * @param id               database ID
  * @param name             name of the app
  * @param publisherId      owner ID of the app
  * @param status           app status
  * @param privacyPolicy    privacy policy
  * @param test             is app in test mode
  * @param domain           domain of the app
  * @param keyword          comma separated list of keywords about the app
  * @param iabCategoriesIds ids of IAB content categories of the app
  * @param bundle           application bundle or package name
  * @param storeUrl         application store URL for an installed app
  * @param version          application version
  * @author Egor Ilchenko
  */
case class App(
    id: Int,
    name: String,
    publisherId: Int,
    status: Status.Value,
    privacyPolicy: Int,
    test: Boolean,
    domain: Option[String],
    keyword: Option[String],
    iabCategoriesIds: Seq[Int],
    bundle: String,
    storeUrl: String,
    version: String) extends BaseEntity
