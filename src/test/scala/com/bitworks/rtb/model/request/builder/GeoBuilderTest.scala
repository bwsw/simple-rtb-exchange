package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.constant.LocationType
import com.bitworks.rtb.model.request.Geo
import org.scalatest.{FlatSpec, Matchers}

class GeoBuilderTest extends FlatSpec with Matchers{

  "GeoBuilder" should "build Geo correctly" in {
    val geo = Geo(Some(42.42), Some(24.24), Some(LocationType.ipAddress),
      Some("country"), Some("region"), Some("regionFips"), Some("metro"),
      Some("city"), Some("zip"), Some(14), Some("string"))

    val buildedGeo = GeoBuilder.builder
      .withLat(42.42)
      .withLon(24.24)
      .withType(LocationType.ipAddress)
      .withCountry("country")
      .withRegion("region")
      .withRegionfips104("regionFips")
      .withMetro("metro")
      .withCity("city")
      .withZip("zip")
      .withUtcoffset(14)
      .withExt("string")
      .build

    buildedGeo shouldBe geo
  }

}
