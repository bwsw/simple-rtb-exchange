package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.User
import org.scalatest.{FlatSpec, Matchers}

class UserBuilderTest extends FlatSpec with Matchers{

  "UserBuilder" should "build User correctly" in {
    val user = User(Some("id"), Some("buyerid"), Some(2016),
      Some("gender"), Some("keywords"), Some("customdata"),
      Some(GeoBuilder.builder.build), Some(Seq.empty), Some("ext"))

    val buildedUser = UserBuilder.builder
        .withId("id")
        .withBuyerid("buyerid")
        .withYob(2016)
        .withGender("gender")
        .withKeywords("keywords")
        .withCustomdata("customdata")
        .withGeo(GeoBuilder.builder.build)
        .withData(Seq.empty)
        .withExt("ext")
        .build

    buildedUser shouldBe user
  }

}
