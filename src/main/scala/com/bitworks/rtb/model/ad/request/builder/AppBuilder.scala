package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.App
import com.bitworks.rtb.model.request.Content

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.App App]]
  *
  * @author Egor Ilchenko
  */
class AppBuilder {
  private var id: Option[String] = None
  private var sectioncat: Option[Seq[String]] = None
  private var pagecat: Option[Seq[String]] = None
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

  def withContent(c: Content) = {
    content = Some(c)
    this
  }

  /** Returns [[com.bitworks.rtb.model.ad.request.App App]] */
  def build = App(id, sectioncat, pagecat, content)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.App App]]
  *
  * @author Egor Ilchenko
  */
object AppBuilder {
  def apply() = new AppBuilder
}
