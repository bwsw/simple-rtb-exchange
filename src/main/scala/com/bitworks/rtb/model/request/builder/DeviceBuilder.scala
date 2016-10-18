package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.constant.{ConnectionType, DeviceType}
import com.bitworks.rtb.model.request.{Device, Geo}

/** Builder for Device model  */
object DeviceBuilder {
  protected class DeviceBuilder{
    private var ua: Option[String] = None
    private var geo: Option[Geo] = None
    private var dnt: Option[Int] = None
    private var lmt: Option[Int] = None
    private var ip: Option[String] = None
    private var ipv6: Option[String] = None
    private var devicetype: Option[DeviceType.Value] = None
    private var make: Option[String] = None
    private var model: Option[String] = None
    private var os: Option[String] = None
    private var osv: Option[String] = None
    private var hwv: Option[String] = None
    private var h: Option[Int] = None
    private var w: Option[Int] = None
    private var ppi: Option[Int] = None
    private var pxratio: Option[Double] = None
    private var js: Option[Int] = None
    private var flashver: Option[String] = None
    private var language: Option[String] = None
    private var carrier: Option[String] = None
    private var connectiontype: Option[ConnectionType.Value] = None
    private var ifa: Option[String] = None
    private var didsha1: Option[String] = None
    private var didmd5: Option[String] = None
    private var dpidsha1: Option[String] = None
    private var dpidmd5: Option[String] = None
    private var macsha1: Option[String] = None
    private var macmd5: Option[String] = None
    private var ext: Option[Any] = None

    def withUa(s:String) = { ua = Some(s); this}
    def withGeo(g:Geo) = { geo = Some(g); this}
    def withDnt(i:Int) = { dnt = Some(i); this}
    def withLmt(i:Int) = { lmt = Some(i); this}
    def withIp(s:String) = { ip = Some(s); this}
    def withIpv6(s:String) = { ipv6 = Some(s); this}
    def withDevicetype(d:DeviceType.Value) = { devicetype = Some(d); this}
    def withMake(s:String) = { make = Some(s); this}
    def withModel(s:String) = { model = Some(s); this}
    def withOs(s:String) = { os = Some(s); this}
    def withOsv(s:String) = { osv = Some(s); this}
    def withHwv(s:String) = { hwv = Some(s); this}
    def withH(i:Int) = { h = Some(i); this}
    def withW(i:Int) = { w = Some(i); this}
    def withPpi(i:Int) = { ppi = Some(i); this}
    def withPxratio(d:Double) = { pxratio = Some(d); this}
    def withJs(i:Int) = { js = Some(i); this}
    def withFlashver(s:String) = { flashver = Some(s); this}
    def withLanguage(s:String) = { language = Some(s); this}
    def withCarrier(s:String) = { carrier = Some(s); this}
    def withConnectiontype(n:ConnectionType.Value) = { connectiontype = Some(n); this}
    def withIfa(s:String) = { ifa = Some(s); this}
    def withDidsha1(s:String) = { didsha1 = Some(s); this}
    def withDidmd5(s:String) = { didmd5 = Some(s); this}
    def withDpidsha1(s:String) = { dpidsha1 = Some(s); this}
    def withDpidmd5(s:String) = { dpidmd5 = Some(s); this}
    def withMacsha1(s:String) = { macsha1 = Some(s); this}
    def withMacmd5(s:String) = { macmd5 = Some(s); this}
    def withExt(a:Any) = { ext = Some(a); this}

    /** Returns Device */
    def build = Device(ua, geo, dnt, lmt, ip, ipv6, devicetype, make,
        model, os, osv, hwv, h, w, ppi, pxratio, js, flashver, language,
        carrier, connectiontype, ifa, didsha1, didmd5, dpidsha1, dpidmd5,
        macsha1, macmd5, ext)
  }

  /** Returns builder for Device */
  def builder = new DeviceBuilder
}
