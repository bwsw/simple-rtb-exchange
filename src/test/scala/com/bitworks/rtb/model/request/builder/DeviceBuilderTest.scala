package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Device
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.builder.DeviceBuilder DeviceBuilder]].
  *
  * @author Egor Ilchenko
  */
class DeviceBuilderTest extends FlatSpec with Matchers {

  "DeviceBuilder" should "build Device correctly" in {
    val device = Device(
      Some("ua"),
      Some(GeoBuilder().build),
      Some(1),
      Some(2),
      Some("ip"),
      Some("ipv6"),
      Some(8),
      Some("make"),
      Some("model"),
      Some("os"),
      Some("osv"),
      Some("hwv"),
      Some(3),
      Some(4),
      Some(5),
      Some(6),
      Some(7),
      Some("flashver"),
      Some("language"),
      Some("carrier"),
      Some(9),
      Some("ifa"),
      Some("didsha1"),
      Some("didmd5"),
      Some("dpidsha1"),
      Some("dpidmd5"),
      Some("macsha1"),
      Some("macmd5"),
      Some("ext"))

    val builder = DeviceBuilder()
    device.ua.foreach(ua => builder.withUa(ua))
    device.geo.foreach(geo => builder.withGeo(geo))
    device.dnt.foreach(dnt => builder.withDnt(dnt))
    device.lmt.foreach(lmt => builder.withLmt(lmt))
    device.ip.foreach(ip => builder.withIp(ip))
    device.ipv6.foreach(ipv6 => builder.withIpv6(ipv6))
    device.deviceType.foreach(deviceType => builder.withDeviceType(deviceType))
    device.make.foreach(make => builder.withMake(make))
    device.model.foreach(model => builder.withModel(model))
    device.os.foreach(os => builder.withOs(os))
    device.osv.foreach(osv => builder.withOsv(osv))
    device.hwv.foreach(hwv => builder.withHwv(hwv))
    device.h.foreach(h => builder.withH(h))
    device.w.foreach(w => builder.withW(w))
    device.ppi.foreach(ppi => builder.withPpi(ppi))
    device.pxRatio.foreach(pxRatio => builder.withPxRatio(pxRatio))
    device.js.foreach(js => builder.withJs(js))
    device.flashVer.foreach(flashVer => builder.withFlashVer(flashVer))
    device.language.foreach(language => builder.withLanguage(language))
    device.carrier.foreach(carrier => builder.withCarrier(carrier))
    device.connectionType.foreach(connectionType => builder.withConnectionType(connectionType))
    device.ifa.foreach(ifa => builder.withIfa(ifa))
    device.didsha1.foreach(didsha1 => builder.withDidsha1(didsha1))
    device.didmd5.foreach(didmd5 => builder.withDidmd5(didmd5))
    device.dpidsha1.foreach(dpidsha1 => builder.withDpidsha1(dpidsha1))
    device.dpidmd5.foreach(dpidmd5 => builder.withDpidmd5(dpidmd5))
    device.macsha1.foreach(macsha1 => builder.withMacsha1(macsha1))
    device.macmd5.foreach(macmd5 => builder.withMacmd5(macmd5))
    device.ext.foreach(ext => builder.withExt(ext))

    val builtDevice = builder.build

    builtDevice shouldBe device
  }

}
