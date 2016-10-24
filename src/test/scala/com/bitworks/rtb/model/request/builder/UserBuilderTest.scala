package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.User
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.UserBuilder UserBuilder]].
  *
  * @author Egor Ilchenko
  */
class UserBuilderTest extends FlatSpec with Matchers {

  "UserBuilder" should "build User correctly" in {
    val user = User(
      Some("id"),
      Some("buyerid"),
      Some(2016),
      Some("gender"),
      Some("keywords"),
      Some("customdata"),
      Some(GeoBuilder().build),
      Some(Seq.empty),
      Some("ext"))

    val builder = UserBuilder()
    user.id.foreach(id => builder.withId(id))
    user.buyerId.foreach(buyerId => builder.withBuyerId(buyerId))
    user.yob.foreach(yob => builder.withYob(yob))
    user.gender.foreach(gender => builder.withGender(gender))
    user.keywords.foreach(keywords => builder.withKeywords(keywords))
    user.customData.foreach(customData => builder.withCustomData(customData))
    user.geo.foreach(geo => builder.withGeo(geo))
    user.data.foreach(data => builder.withData(data))
    user.ext.foreach(ext => builder.withExt(ext))

    val builtUser = builder.build

    builtUser shouldBe user
  }

}
