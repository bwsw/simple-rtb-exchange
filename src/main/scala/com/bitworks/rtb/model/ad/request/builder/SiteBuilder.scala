package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.Site
import com.bitworks.rtb.model.request.Content

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.Site Site]]
  *
  * @author Egor Ilchenko
  */
class SiteBuilder {
  private var id: Option[String] = None
  private var sectioncat: Option[Seq[String]] = None
  private var pagecat: Option[Seq[String]] = None
  private var page: Option[String] = None
  private var ref: Option[String] = None
  private var search: Option[String] = None
  private var mobile: Option[Int] = None
  private var content: Option[Content] = None

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withSectioncat(s: Seq[String]) = {
    sectioncat = Some(s)
    this
  }

  def withPagecat(s: Seq[String]) = {
    pagecat = Some(s)
    this
  }

  def withPage(s: String) = {
    page = Some(s)
    this
  }

  def withRef(s: String) = {
    ref = Some(s)
    this
  }

  def withSearch(s: String) = {
    search = Some(s)
    this
  }

  def withMobile(i: Int) = {
    mobile = Some(i)
    this
  }

  def withContent(c: Content) = {
    content = Some(c)
    this
  }


  /** Returns [[com.bitworks.rtb.model.ad.request.Site Site]] */
  def build = Site(id, sectioncat, pagecat, page, ref, search, mobile, content)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.Site Site]]
  *
  * @author Egor Ilchenko
  */
object SiteBuilder {
  def apply() = new SiteBuilder
}
