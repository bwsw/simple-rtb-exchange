package com.bitworks.rtb.model.request

/**
  * A non-browser application in which the ad will be shown.
  *
  * @param id            exchange-specific app ID
  * @param name          app name
  * @param bundle        application bundle or package name
  * @param domain        domain of the app
  * @param storeUrl      app store URL for an installed app; for QAG 1.5 compliance
  * @param cat           IAB content categories of the app
  * @param sectionCat    IAB content categories that describe the current section of the app
  * @param pageCat       IAB content categories that describe the current page or view of the app
  * @param ver           application version
  * @param privacyPolicy indicates if the app has a privacy policy, where 0 = no, 1 = yes
  * @param paid          indicates if the app is a paid version where 0 = app is free, 1 = the app
  *                      is a paid version
  * @param publisher     details about the [[com.bitworks.rtb.model.request.Publisher Publisher]] of
  *                      the app
  * @param content       details about the [[com.bitworks.rtb.model.request.Content Content]] with
  *                      the app
  * @param keywords      comma separated list of keywords about the app
  * @param ext           placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  */
case class App(
    id: Option[String],
    name: Option[String],
    bundle: Option[String],
    domain: Option[String],
    storeUrl: Option[String],
    cat: Option[Seq[String]],
    sectionCat: Option[Seq[String]],
    pageCat: Option[Seq[String]],
    ver: Option[String],
    privacyPolicy: Option[Int],
    paid: Option[Int],
    publisher: Option[Publisher],
    content: Option[Content],
    keywords: Option[String],
    ext: Option[Any])
