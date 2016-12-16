package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Data, Geo, User}

/**
  * Builder for [[com.bitworks.rtb.model.request.User User]].
  *
  * @author Egor Ilchenko
  */
class UserBuilder private {
  private var id: Option[String] = None
  private var buyerId: Option[String] = None
  private var yob: Option[Int] = None
  private var gender: Option[String] = None
  private var keywords: Option[String] = None
  private var customData: Option[String] = None
  private var geo: Option[Geo] = None
  private var data: Option[Seq[Data]] = None
  private var ext: Option[Any] = None

  def withId(s: String) = {
    id = Some(s)
    this
  }

  def withBuyerId(s: String) = {
    buyerId = Some(s)
    this
  }

  def withYob(i: Int) = {
    yob = Some(i)
    this
  }

  def withGender(s: String) = {
    gender = Some(s)
    this
  }

  def withKeywords(s: String) = {
    keywords = Some(s)
    this
  }

  def withCustomData(s: String) = {
    customData = Some(s)
    this
  }

  def withGeo(g: Geo) = {
    geo = Some(g)
    this
  }

  def withData(s: Seq[Data]) = {
    data = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns User */
  def build = User(id, buyerId, yob, gender, keywords, customData, geo, data, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.User User]].
  *
  * @author Egor Ilchenko
  */
object UserBuilder {
  def apply() = new UserBuilder
}
