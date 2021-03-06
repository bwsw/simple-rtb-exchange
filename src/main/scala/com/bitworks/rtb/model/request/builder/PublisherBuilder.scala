package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Publisher

/**
  * Builder for [[com.bitworks.rtb.model.request.Publisher Publisher]].
  *
  * @author Pavel Tomskikh
  */
class PublisherBuilder private {
  private var id: Option[String] = None
  private var name: Option[String] = None
  private var cat: Option[Seq[String]] = None
  private var domain: Option[String] = None
  private var ext: Option[Any] = None

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withName(s: String) = {
    name = Some(s)
    this
  }

  def withCat(s: Seq[String]) = {
    cat = Some(s)
    this
  }

  def withDomain(s: String) = {
    domain = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = Publisher(id, name, cat, domain, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.Publisher Publisher]].
  *
  * @author Pavel Tomskikh
  */
object PublisherBuilder {
  def apply() = new PublisherBuilder
}
