package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{App, Content, Publisher}

/**
  * Builder for [[com.bitworks.rtb.model.request.App]]
  *
  * @author Pavel Tomskikh
  *
  */
class AppBuilder private {
  private var id: Option[String] = None
  private var name: Option[String] = None
  private var bundle: Option[String] = None
  private var domain: Option[String] = None
  private var storeUrl: Option[String] = None
  private var cat: Option[Seq[String]] = None
  private var sectionCat: Option[Seq[String]] = None
  private var pageCat: Option[Seq[String]] = None
  private var ver: Option[String] = None
  private var privacyPolicy: Option[Int] = None
  private var paid: Option[Int] = None
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

  def withBundle(s: String) = {
    bundle = Some(s)
    this
  }

  def withDomain(s: String) = {
    domain = Some(s)
    this
  }

  def withStoreUrl(s: String) = {
    storeUrl = Some(s)
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

  def withVer(s: String) = {
    ver = Some(s)
    this
  }

  def withPrivacyPolicy(i: Int) = {
    privacyPolicy = Some(i)
    this
  }

  def withPaid(i: Int) = {
    paid = Some(i)
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

  def build = App(
    id,
    name,
    bundle,
    domain,
    storeUrl,
    cat,
    sectionCat,
    pageCat,
    ver,
    privacyPolicy,
    paid,
    publisher,
    content,
    keyWords,
    ext)
}

object AppBuilder {
  def apply() = new AppBuilder
}
