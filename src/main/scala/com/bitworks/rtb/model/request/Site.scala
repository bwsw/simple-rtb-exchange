package com.bitworks.rtb.model.request

/**
  * Information about website in which the ad will be shown.
  *
  * @param id            exchange-specific site ID
  * @param name          site name
  * @param domain        domain of the site
  * @param cat           IAB content categories of the site
  * @param sectionCat    IAB content categories that describe the current section of the site
  * @param pageCat       IAB content categories that describe the current page or view of the site
  * @param page          URL of the page where the impression will be shown
  * @param ref           referrer URL that caused navigation to the current page
  * @param search        search string that caused navigation to the current page
  * @param mobile        mobile-optimized signal, where 0 = no, 1 = yes
  * @param privacyPolicy indicates if the site has a privacy policy, where 0 = no, 1 = yes
  * @param publisher     details about the [[com.bitworks.rtb.model.request.Publisher Publisher]]
  * @param content       details about the [[com.bitworks.rtb.model.request.Content Content]]
  * @param keywords      comma separated list of keywords about the site
  * @param ext           placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/17/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
case class Site(
    id: Option[String],
    name: Option[String],
    domain: Option[String],
    cat: Option[Seq[String]],
    sectionCat: Option[Seq[String]],
    pageCat: Option[Seq[String]],
    page: Option[String],
    ref: Option[String],
    search: Option[String],
    mobile: Option[Int],
    privacyPolicy: Option[Int],
    publisher: Option[Publisher],
    content: Option[Content],
    keywords: Option[String],
    ext: Option[Any])
