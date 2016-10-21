package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Device
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.Device]]
  *
  * @author Egor Ilchenko
  *
  */
class DeviceBuilderTest extends FlatSpec with Matchers{

  "DeviceBuilder" should "build Device correctly" in {
    val device = Device(Some("ua"), Some(GeoBuilder().build), Some(1), Some(2),
      Some("ip"), Some("ipv6"), Some(8), Some("make"),
      Some("model"), Some("os"), Some("osv"), Some("hwv"), Some(3),
      Some(4), Some(5), Some(6), Some(7), Some("flashver"),
      Some("language"), Some("carrier"), Some(9), Some("ifa"),
      Some("didsha1"), Some("didmd5"), Some("dpidsha1"), Some("dpidmd5"),
      Some("macsha1"), Some("macmd5"), Some("ext"))

    val buildedDevice = DeviceBuilder()
      .withUa("ua")
      .withGeo(GeoBuilder().build)
      .withDnt(1)
      .withLmt(2)
      .withIp("ip")
      .withIpv6("ipv6")
      .withDeviceType(8)
      .withMake("make")
      .withModel("model")
      .withOs("os")
      .withOsv("osv")
      .withHwv("hwv")
      .withH(3)
      .withW(4)
      .withPpi(5)
      .withPxRatio(6)
      .withJs(7)
      .withFlashVer("flashver")
      .withLanguage("language")
      .withCarrier("carrier")
      .withConnectionType(9)
      .withIfa("ifa")
      .withDidsha1("didsha1")
      .withDidmd5("didmd5")
      .withDpidsha1("dpidsha1")
      .withDpidmd5("dpidmd5")
      .withMacsha1("macsha1")
      .withMacmd5("macmd5")
      .withExt("ext")
      .build

    buildedDevice shouldBe device
  }

}
