package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Geo

/**
  * Builder for [[com.bitworks.rtb.model.request.Geo]]
  *
  * @author Egor Ilchenko
  *
  */
class GeoBuilder private {
  private var lat: Option[Float] = None
  private var lon: Option[Float] = None
  private var `type`: Option[Int] = None
  private var country: Option[String] = None
  private var region: Option[String] = None
  private var regionFips104: Option[String] = None
  private var metro: Option[String] = None
  private var city: Option[String] = None
  private var zip: Option[String] = None
  private var utcOffset: Option[Int] = None
  private var ext: Option[Any] = None

  def withLat(d: Float) = {
    lat = Some(d)
    this
  }

  def withLon(d: Float) = {
    lon = Some(d)
    this
  }

  def withType(i: Int) = {
    `type` = Some(i)
    this
  }

  def withCountry(s: String) = {
    country = Some(s)
    this
  }

  def withRegion(s: String) = {
    region = Some(s)
    this
  }

  def withRegionFips104(s: String) = {
    regionFips104 = Some(s)
    this
  }

  def withMetro(s: String) = {
    metro = Some(s)
    this
  }

  def withCity(s: String) = {
    city = Some(s)
    this
  }

  def withZip(s: String) = {
    zip = Some(s)
    this
  }

  def withUtcOffset(i: Int) = {
    utcOffset = Some(i)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Geo */
  def build = Geo(lat, lon, `type`, country, region, regionFips104, metro, city, zip, utcOffset,
    ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.Geo]]
  *
  * @author Egor Ilchenko
  *
  */
object GeoBuilder {
  def apply() = new GeoBuilder
}
