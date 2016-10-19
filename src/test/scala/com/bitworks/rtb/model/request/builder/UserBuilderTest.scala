package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.User
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class UserBuilderTest extends FlatSpec with Matchers{

  "UserBuilder" should "build User correctly" in {
    val user = User(Some("id"), Some("buyerid"), Some(2016),
      Some("gender"), Some("keywords"), Some("customdata"),
      Some(GeoBuilder().build), Some(Seq.empty), Some("ext"))

    val buildedUser = UserBuilder()
        .withId("id")
        .withBuyerid("buyerid")
        .withYob(2016)
        .withGender("gender")
        .withKeywords("keywords")
        .withCustomdata("customdata")
        .withGeo(GeoBuilder().build)
        .withData(Seq.empty)
        .withExt("ext")
        .build

    buildedUser shouldBe user
  }

}
