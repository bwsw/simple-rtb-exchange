package com.bitworks.rtb.request

/** Describes the non-browser application in which the ad will be shown.
  *
  * @param id            exchange-specific app ID
  * @param name          app name
  * @param bundle        application bundle or package name
  * @param domain        domain of the app
  * @param storeUrl      app store URL for an installed app
  * @param cat           IAB content categories of the app
  * @param sectionCat    IAB content categories that describe the current section of the app
  * @param pageCat       IAB content categories that describe the current page or view of the app
  * @param ver           application version
  * @param privacyPolicy indicates if the app has a privacy policy
  * @param paid          indicates if the app is a paid version
  * @param publisher     details about the Publisher
  * @param content       details about the Content
  * @param keyWords      comma separated list of keywords about the app
  * @param ext           placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/17/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
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
    privacyPolicy: Option[Boolean],
    paid: Option[Boolean],
    publisher: Option[Publisher],
    content: Option[Content],
    keyWords: Option[String],
    ext: Option[Any])
