package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.constant.LocationType
import com.bitworks.rtb.model.request.Geo

/** Builder for Geo model  */
class GeoBuilder private{
  private var lat: Option[Float] = None
  private var lon: Option[Float] = None
  private var `type`: Option[LocationType.Value] = None
  private var country: Option[String] = None
  private var region: Option[String] = None
  private var regionfips104: Option[String] = None
  private var metro: Option[String] = None
  private var city: Option[String] = None
  private var zip: Option[String] = None
  private var utcoffset: Option[Int] = None
  private var ext: Option[Any] = None

  def withLat(d:Float) = { lat = Some(d); this}
  def withLon(d:Float) = { lon = Some(d); this}
  def withType(l:LocationType.Value) = { `type` = Some(l); this}
  def withCountry(s:String) = { country = Some(s); this}
  def withRegion(s:String) = { region = Some(s); this}
  def withRegionfips104(s:String) = { regionfips104 = Some(s); this}
  def withMetro(s:String) = { metro = Some(s); this}
  def withCity(s:String) = { city = Some(s); this}
  def withZip(s:String) = { zip = Some(s); this}
  def withUtcoffset(i:Int) = { utcoffset = Some(i); this}
  def withExt(a:Any) = { ext = Some(a); this}

  /** Returns Geo */
  def build = Geo(lat, lon, `type`, country, region, regionfips104, metro, city, zip, utcoffset, ext)
}

/** Builder for Geo model  */
object GeoBuilder {
  def apply() = new GeoBuilder
}
