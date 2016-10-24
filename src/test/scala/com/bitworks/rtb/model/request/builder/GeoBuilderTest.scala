package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Geo
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.GeoBuilder GeoBuilder]].
  *
  * @author Egor Ilchenko
  */
class GeoBuilderTest extends FlatSpec with Matchers {

  "GeoBuilder" should "build Geo correctly" in {
    val geo = Geo(
      Some(42.42.toFloat),
      Some(24.24.toFloat),
      Some(1),
      Some("country"),
      Some("region"),
      Some("regionFips"),
      Some("metro"),
      Some("city"),
      Some("zip"),
      Some(14),
      Some("string"))

    val builder = GeoBuilder()
    geo.lat.foreach(lat => builder.withLat(lat))
    geo.lon.foreach(lon => builder.withLon(lon))
    geo.`type`.foreach(`type` => builder.withType(`type`))
    geo.country.foreach(country => builder.withCountry(country))
    geo.region.foreach(region => builder.withRegion(region))
    geo.regionFips104.foreach(regionFips104 => builder.withRegionFips104(regionFips104))
    geo.metro.foreach(metro => builder.withMetro(metro))
    geo.city.foreach(city => builder.withCity(city))
    geo.zip.foreach(zip => builder.withZip(zip))
    geo.utcOffset.foreach(utcOffset => builder.withUtcOffset(utcOffset))
    geo.ext.foreach(ext => builder.withExt(ext))

    val builtGeo = builder.build

    builtGeo shouldBe geo
  }

}
