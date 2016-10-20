package com.bitworks.rtb.model.request

/** Information pertaining to the device through which the user is interacting.
  *
  * Device information includes its hardware, platform, location, and carrier data.
  * The device can refer to a mobile handset, a desktop computer, set top box, or other digital device.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * @param ua Browser user agent string.
  * @param geo Location of the device assumed to be the user’s current location.
  * @param dnt Standard “Do Not Track” flag as set in the header by the browser,
  *            where 0 = tracking is unrestricted, 1 = do not track.
  * @param lmt “Limit Ad Tracking” signal commercially endorsed (e.g., iOS, Android),
  *            where 0 = tracking is unrestricted, 1 = tracking must be limited per commercial guidelines.
  * @param ip IPv4 address closest to device.
  * @param ipv6 IP address closest to device as IPv6.
  * @param devicetype The general type of device.
  * @param make Device make (e.g., “Apple”).
  * @param model Device model (e.g., “iPhone”).
  * @param os Device operating system (e.g., “iOS”).
  * @param osv Device operating system version (e.g., “3.1.2”).
  * @param hwv Hardware version of the device (e.g., “5S” for iPhone 5S).
  * @param h Physical height of the screen in pixels.
  * @param w Physical width of the screen in pixels.
  * @param ppi Screen size as pixels per linear inch.
  * @param pxratio The ratio of physical pixels to device independent pixels.
  * @param js Support for JavaScript, where 0 = no, 1 = yes.
  * @param flashver Version of Flash supported by the browser.
  * @param language Browser language using ISO-639-1-alpha-2.
  * @param carrier Carrier or ISP (e.g., “VERIZON”). “WIFI” is often used in mobile
  *                to indicate high bandwidth (e.g., video friendly vs. cellular).
  * @param connectiontype Network connection type.
  * @param ifa ID sanctioned for advertiser use in the clear (i.e., not hashed).
  * @param didsha1 Hardware device ID (e.g., IMEI); hashed via SHA1.
  * @param didmd5 Hardware device ID (e.g., IMEI); hashed via MD5.
  * @param dpidsha1 Platform device ID (e.g., Android ID); hashed via SHA1.
  * @param dpidmd5 Platform device ID (e.g., Android ID); hashed via MD5.
  * @param macsha1 MAC address of the device; hashed via SHA1.
  * @param macmd5 MAC address of the device; hashed via MD5
  * @param ext Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Device (
    ua: Option[String],
    geo: Option[Geo],
    dnt: Option[Int],
    lmt: Option[Int],
    ip: Option[String],
    ipv6: Option[String],
    devicetype: Option[Int],
    make: Option[String],
    model: Option[String],
    os: Option[String],
    osv: Option[String],
    hwv: Option[String],
    h: Option[Int],
    w: Option[Int],
    ppi: Option[Int],
    pxratio: Option[Double],
    js: Option[Int],
    flashver: Option[String],
    language: Option[String],
    carrier: Option[String],
    connectiontype: Option[Int],
    ifa: Option[String],
    didsha1: Option[String],
    didmd5: Option[String],
    dpidsha1: Option[String],
    dpidmd5: Option[String],
    macsha1: Option[String],
    macmd5: Option[String],
    ext: Option[Any])
