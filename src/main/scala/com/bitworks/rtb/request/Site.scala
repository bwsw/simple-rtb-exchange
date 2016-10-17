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
            val id: String,
            val name: String,
            val domain: String,
            val cat: Array[String],
            val sectionCat: Array[String],
            val pageCat: Array[String],
            val page: String,
            val ref: String,
            val search: String,
            val mobile: Boolean,
            val privacyPolicy: Boolean,
            val publisher: Publisher,
            val content: Content,
            val keyWords: String
          ) {

}
