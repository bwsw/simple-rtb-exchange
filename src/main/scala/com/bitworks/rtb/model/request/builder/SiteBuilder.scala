package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Content, Publisher, Site}

/** Builder for [[com.bitworks.rtb.model.request.Site]]
  *
  * Created on: 10/19/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class SiteBuilder private {
  private var id: Option[String] = None
  private var name: Option[String] = None
  private var domain: Option[String] = None
  private var cat: Option[Seq[String]] = None
  private var sectionCat: Option[Seq[String]] = None
  private var pageCat: Option[Seq[String]] = None
  private var page: Option[String] = None
  private var ref: Option[String] = None
  private var search: Option[String] = None
  private var mobile: Option[Boolean] = None
  private var privacyPolicy: Option[Boolean] = None
  private var publisher: Option[Publisher] = None
  private var content: Option[Content] = None
  private var keyWords: Option[String] = None
  private var ext: Option[Any] = None

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withName(s: String) = {
    name = Some(s)
    this
  }

  def withDomain(s: String) = {
    domain = Some(s)
    this
  }

  def withCat(s: Seq[String]) = {
    cat = Some(s)
    this
  }

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

  def isPrivacyPolicy(b: Boolean) = {
    privacyPolicy = Some(b)
    this
  }

  def isMobile(b: Boolean) = {
    mobile = Some(b)
    this
  }

  def withPublisher(p: Publisher) = {
    publisher = Some(p)
    this
  }

  def withContent(c: Content) = {
    content = Some(c)
    this
  }

  def withKeyWords(s: String) = {
    keyWords = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Site(
    id,
    name,
    domain,
    cat,
    sectionCat,
    pageCat,
    page,
    ref,
    search,
    mobile,
    privacyPolicy,
    publisher,
    content,
    keyWords,
    ext)

}

object SiteBuilder {
  def apply() = new SiteBuilder
}
