package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.constant.LocationType
import com.bitworks.rtb.model.request.Geo

/** Builder for Geo model  */
object GeoBuilder {
  protected class GeoBuilder{
    private var lat: Option[Double] = None
    private var lon: Option[Double] = None
    private var `type`: Option[LocationType.Value] = None
    private var country: Option[String] = None
    private var region: Option[String] = None
    private var regionfips104: Option[String] = None
    private var metro: Option[String] = None
    private var city: Option[String] = None
    private var zip: Option[String] = None
    private var utcoffset: Option[Int] = None
    private var ext: Option[Any] = None

    def withLat(d:Double) = { lat = Some(d); this}
    def withLon(d:Double) = { lon = Some(d); this}
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
  /** Returns builder for Geo */
  def builder = new GeoBuilder
}
