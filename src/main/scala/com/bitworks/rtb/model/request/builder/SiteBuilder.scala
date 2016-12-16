package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Content, Publisher, Site}

/**
  * Builder for [[com.bitworks.rtb.model.request.Site Site]].
  *
  * @author Pavel Tomskikh
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
  private var mobile: Option[Int] = None
  private var privacyPolicy: Option[Int] = None
  private var publisher: Option[Publisher] = None
  private var content: Option[Content] = None
  private var keywords: Option[String] = None
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

  def withMobile(i: Int) = {
    mobile = Some(i)
    this
  }

  def withPrivacyPolicy(i: Int) = {
    privacyPolicy = Some(i)
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

  def withKeywords(s: String) = {
    keywords = Some(s)
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
    keywords,
    ext)

}

/**
  * Builder for [[com.bitworks.rtb.model.request.Site Site]].
  *
  * @author Pavel Tomskikh
  */
object SiteBuilder {
  def apply() = new SiteBuilder
}
