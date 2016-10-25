package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.Site
import com.bitworks.rtb.model.request.Content

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.Site Site]].
  *
  * @param id value of id in [[com.bitworks.rtb.model.ad.request.Site Site]]
  * @author Egor Ilchenko
  */
class SiteBuilder(id: Int) {
  private var sectionCat: Option[Seq[String]] = None
  private var pageCat: Option[Seq[String]] = None
  private var page: Option[String] = None
  private var ref: Option[String] = None
  private var search: Option[String] = None
  private var mobile: Option[Int] = None
  private var content: Option[Content] = None

  def withSectionCat(s: Seq[String]) = {
    sectionCat = Some(s)
    this
  }

  def withPageCat(s: Seq[String]) = {
    pageCat = Some(s)
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
  def build = Site(id, sectionCat, pageCat, page, ref, search, mobile, content)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.Site Site]].
  *
  * @author Egor Ilchenko
  */
object SiteBuilder {
  def apply(id: Int) = new SiteBuilder(id)
}
