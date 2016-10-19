package com.bitworks.rtb.request

/** Describes the website in which the ad will be shown.
  *
  * @param id exchange-specific site ID
  * @param name site name
  * @param domain domain of the site
  * @param cat IAB content categories of the site
  * @param sectionCat IAB content categories that describe the current section of the site
  * @param pageCat IAB content categories that describe the current page or view of the site
  * @param page URL of the page where the impression will be shown
  * @param ref referrer URL that caused navigation to the current page
  * @param search search string that caused navigation to the current page
  * @param mobile indicates if the site is mobile-optimized
  * @param privacyPolicy indicates if the site has a privacy policy
  * @param publisher details about the Publisher
  * @param content details about the Content
  * @param keyWords comma separated list of keywords about the site
  */
class Site(
    id: Option[String],
    name: Option[String],
    domain: Option[String],
    cat: Option[Array[String]],
    sectionCat: Option[Array[String]],
    pageCat: Option[Array[String]],
    page: Option[String],
    ref: Option[String],
    search: Option[String],
    mobile: Option[Boolean],
    privacyPolicy: Option[Boolean],
    publisher: Option[Publisher],
    content: Option[Content],
    keyWords: Option[String])
