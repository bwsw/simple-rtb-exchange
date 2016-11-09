package com.bitworks.rtb.model.db

/**
  * Details of the application calling for the impression.
  *
  * @param id               database ID
  * @param name             name of the application
  * @param publisherId      owner ID of the application
  * @param status           application status
  * @param privacyPolicy    privacy policy
  * @param test             indicates if the application is in test mode
  * @param domain           domain of the application
  * @param keyword          comma separated list of keywords about the application
  * @param iabCategoriesIds ids of IAB content categories of the application
  * @param bundle           applicationlication bundle or package name
  * @param storeUrl         application store URL for an installed application
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
