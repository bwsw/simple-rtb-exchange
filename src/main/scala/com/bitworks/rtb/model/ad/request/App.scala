package com.bitworks.rtb.model.ad.request

import com.bitworks.rtb.model.request.Content

/**
  * Details of the application calling for the impression.
  *
  * @param id         exchange-specific ID
  * @param sectioncat IAB content categories of the current section
  * @param pagecat    IAB content categories of the current page
  * @param content    [[com.bitworks.rtb.model.request.Content Content]] object
  * @author Egor Ilchenko
  */
case class App(
    id: Option[String],
    sectioncat: Option[Seq[String]],
    pagecat: Option[Seq[String]],
    content: Option[Content])
