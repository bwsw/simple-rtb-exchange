package com.bitworks.rtb.request

/** Describes the non-browser application in which the ad will be shown.
  *
  * @param id exchange-specific app ID
  * @param name app name
  * @param bundle application bundle or package name
  * @param domain domain of the app
  * @param storeUrl app store URL for an installed app
  * @param cat IAB content categories of the app
  * @param sectionCat IAB content categories that describe the current section of the app
  * @param pageCat IAB content categories that describe the current page or view of the app
  * @param ver application version
  * @param privacyPolicy indicates if the app has a privacy policy
  * @param paid indicates if the app is a paid version
  * @param publisher details about the Publisher
  * @param content details about the Content
  * @param keyWords comma separated list of keywords about the app
  */
class App(
           val id: String,
           val name: String,
           val bundle: String,
           val domain: String,
           val storeUrl: String,
           val cat: Array[String],
           val sectionCat: Array[String],
           val pageCat: Array[String],
           val ver: String,
           val privacyPolicy: Boolean,
           val paid: Boolean,
           val publisher: Publisher,
           val content: Content,
           val keyWords: String
         ) {

}
