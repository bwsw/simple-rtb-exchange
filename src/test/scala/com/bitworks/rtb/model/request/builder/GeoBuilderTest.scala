package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Geo
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.Geo]]
  *
  * @author Egor Ilchenko
  *
  */
class GeoBuilderTest extends FlatSpec with Matchers{

  "GeoBuilder" should "build Geo correctly" in {
    val geo = Geo(Some(42.42.toFloat), Some(24.24.toFloat), Some(1),
      Some("country"), Some("region"), Some("regionFips"), Some("metro"),
      Some("city"), Some("zip"), Some(14), Some("string"))

    val buildedGeo = GeoBuilder()
      .withLat(42.42.toFloat)
      .withLon(24.24.toFloat)
      .withType(1)
      .withCountry("country")
      .withRegion("region")
      .withRegionFips104("regionFips")
      .withMetro("metro")
      .withCity("city")
      .withZip("zip")
      .withUtcOffset(14)
      .withExt("string")
      .build

    buildedGeo shouldBe geo
  }

}
