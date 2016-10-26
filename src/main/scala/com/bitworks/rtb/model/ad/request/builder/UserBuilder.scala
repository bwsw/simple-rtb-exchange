package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.User
import com.bitworks.rtb.model.request.Geo

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.User User]].
  *
  * @author Egor Ilchenko
  */
class UserBuilder {
  private var id: Option[String] = None
  private var yob: Option[Int] = None
  private var gender: Option[String] = None
  private var keywords: Option[String] = None
  private var geo: Option[Geo] = None

  def withId(s: String) = {
    id = Some(s)
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

  def withGeo(g: Geo) = {
    geo = Some(g)
    this
  }

  /** Returns [[com.bitworks.rtb.model.ad.request.User User]] */
  def build = User(id, yob, gender, keywords, geo)
}

/**
  * Builder for [[com.bitworks.rtb.model.ad.request.User User]].
  *
  * @author Egor Ilchenko
  */
object UserBuilder {
  def apply() = new UserBuilder
}
