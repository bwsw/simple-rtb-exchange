package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.App
import com.bitworks.rtb.model.request.Content

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.App App]].
  *
  * @param id value of id in [[com.bitworks.rtb.model.ad.request.App App]]
  * @author Egor Ilchenko
  */
class AppBuilder(id: Int) {
  private var sectionCat: Option[Seq[String]] = None
  private var pageCat: Option[Seq[String]] = None
  private var content: Option[Content] = None

  def withSectionCat(s: Seq[String]) = {
    sectionCat = Some(s)
    this
  }

  def withPageCat(s: Seq[String]) = {
    pageCat = Some(s)
    this
  }

  def withContent(c: Content) = {
    content = Some(c)
    this
  }

  /** Returns [[com.bitworks.rtb.model.ad.request.App App]] */
  def build = App(id, sectionCat, pageCat, content)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.App App]].
  *
  * @author Egor Ilchenko
  */
object AppBuilder {
  def apply(id: Int) = new AppBuilder(id)
}
