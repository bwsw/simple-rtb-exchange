package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.User
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.UserBuilder UserBuilder]].
  *
  * @author Egor Ilchenko
  *
  */
class UserBuilderTest extends FlatSpec with Matchers{

  "UserBuilder" should "build User correctly" in {
    val user = User(Some("id"), Some("buyerid"), Some(2016),
      Some("gender"), Some("keywords"), Some("customdata"),
      Some(GeoBuilder().build), Some(Seq.empty), Some("ext"))

    val builtUser = UserBuilder()
        .withId("id")
        .withBuyerId("buyerid")
        .withYob(2016)
        .withGender("gender")
        .withKeywords("keywords")
        .withCustomData("customdata")
        .withGeo(GeoBuilder().build)
        .withData(Seq.empty)
        .withExt("ext")
        .build

    builtUser shouldBe user
  }

}
