package com.bitworks.rtb.model.ad.request.builder

import com.bitworks.rtb.model.ad.request.User
import com.bitworks.rtb.model.request.builder.GeoBuilder
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.request.builder.UserBuilder UserBuilder]].
  *
  * @author Egor Ilchenko
  */
class UserBuilderTest extends FlatSpec with Matchers {

  "UserBuilder" should "build User correctly" in {
    val user = User(
      Some("id"),
      Some(2000),
      Some("M"),
      Some("keywords"),
      Some(GeoBuilder().build))

    val builder = UserBuilder()
    user.id.foreach(id => builder.withId(id))
    user.yob.foreach(yob => builder.withYob(yob))
    user.gender.foreach(gender => builder.withGender(gender))
    user.keywords.foreach(keywords => builder.withKeywords(keywords))
    user.geo.foreach(geo => builder.withGeo(geo))

    val builtUser = builder.build

    builtUser shouldBe user
  }

  it should "build User with default values correctly" in {
    val user = User(
      None,
      None,
      None,
      None,
      None)

    val builtUser = UserBuilder().build

    builtUser shouldBe user
  }

}
