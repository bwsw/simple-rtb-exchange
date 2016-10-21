package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Producer

/**
  * Builder for [[com.bitworks.rtb.model.request.Producer]]
  *
  * Created on: 10/17/2016
  *
  * @author Pavel Tomskikh
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ProducerBuilder private {
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

  def build = Producer(id, name, cat, domain, ext)
}

object ProducerBuilder {
  def apply() = new ProducerBuilder
}
