package com.bitworks.rtb.request

/** Describes the website in which the ad will be shown.
  *
  * @param id            exchange-specific site ID
  * @param name          site name
  * @param domain        domain of the site
  * @param cat           IAB content categories of the site
  *                      See List 5.1 Content Categories in
  *                      OpenRTB API Specification Version 2.3 for details.
  * @param sectionCat    IAB content categories that describe the current section of the site
  *                      See List 5.1 Content Categories in
  *                      OpenRTB API Specification Version 2.3 for details.
  * @param pageCat       IAB content categories that describe the current page or view of the site
  *                      See List 5.1 Content Categories in
  *                      OpenRTB API Specification Version 2.3 for details.
  * @param page          URL of the page where the impression will be shown
  * @param ref           referrer URL that caused navigation to the current page
  * @param search        search string that caused navigation to the current page
  * @param mobile        indicates if the site is mobile-optimized
  * @param privacyPolicy indicates if the site has a privacy policy
  * @param publisher     details about the Publisher
  * @param content       details about the Content
  * @param keyWords      comma separated list of keywords about the site
  * @param ext           placeholder for exchange-specific extensions to OpenRTB
  *
  * Created on: 10/17/2016
  *
  * @author Tomskih Pavel
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
    mobile: Option[Boolean],
    privacyPolicy: Option[Boolean],
    publisher: Option[Publisher],
    content: Option[Content],
    keyWords: Option[String],
    ext: Option[Any])
