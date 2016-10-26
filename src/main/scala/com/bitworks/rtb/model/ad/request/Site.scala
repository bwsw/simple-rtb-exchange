package com.bitworks.rtb.model.ad.request

import com.bitworks.rtb.model.request.Content

/**
  * Details of the website calling for the impression.
  *
  * @param id         exchange-specific site ID
  * @param sectionCat IAB content categories of the current section
  * @param pageCat    IAB content categories of the current page
  * @param page       URL of the page where the impression will be shown
  * @param ref        referrer URL that caused navigation to the current page
  * @param search     search string that caused navigation to the current page
  * @param mobile     mobile-optimized signal, where 0 = no, 1 = yes
  * @param content    [[com.bitworks.rtb.model.request.Content Content]] object
  * @author Egor Ilchenko
  */
case class Site(
    id: Int,
    sectionCat: Option[Seq[String]],
    pageCat: Option[Seq[String]],
    page: Option[String],
    ref: Option[String],
    search: Option[String],
    mobile: Option[Int],
    content: Option[Content])
