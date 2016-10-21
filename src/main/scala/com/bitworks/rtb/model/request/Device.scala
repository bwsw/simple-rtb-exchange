package com.bitworks.rtb.model.request

/**
  * Information pertaining to the device through which the user is interacting.
  *
  * @param ua             browser user agent string
  * @param geo            location of the device assumed to be the user’s current location; see
  *                       [[com.bitworks.rtb.model.request.Geo Geo]]
  * @param dnt            standard “Do Not Track” flag as set in the header by the browser,
  *                       where 0 = tracking is unrestricted, 1 = do not track
  * @param lmt            “Limit Ad Tracking” signal commercially endorsed, where 0 = tracking is
  *                       unrestricted, 1 = tracking must be limited per commercial guidelines
  * @param ip             IPv4 address closest to device
  * @param ipv6           IP address closest to device as IPv6
  * @param devicetype     the general type of device
  * @param make           device make
  * @param model          device model
  * @param os             device operating system
  * @param osv            device operating system version
  * @param hwv            hardware version of the device
  * @param h              physical height of the screen in pixels
  * @param w              physical width of the screen in pixels
  * @param ppi            screen size as pixels per linear inch
  * @param pxratio        the ratio of physical pixels to device independent pixels
  * @param js             support for JavaScript, where 0 = no, 1 = yes
  * @param flashVer       version of Flash supported by the browser
  * @param language       browser language using ISO-639-1-alpha-2
  * @param carrier        carrier or ISP. “WIFI” is often used in mobile to indicate high bandwidth
  * @param connectionType network connection type
  * @param ifa            ID sanctioned for advertiser use in the clear (i.e., not hashed)
  * @param didsha1        hardware device ID hashed via SHA.
  * @param didmd5         hardware device ID hashed via MD5
  * @param dpidsha1       platform device ID hashed via SHA1
  * @param dpidmd5        platform device ID hashed via MD5
  * @param macsha1        MAC address of the device hashed via SHA1
  * @param macmd5         MAC address of the device hashed via MD5
  * @param ext            placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Device(
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
    flashVer: Option[String],
    language: Option[String],
    carrier: Option[String],
    connectionType: Option[Int],
    ifa: Option[String],
    didsha1: Option[String],
    didmd5: Option[String],
    dpidsha1: Option[String],
    dpidmd5: Option[String],
    macsha1: Option[String],
    macmd5: Option[String],
    ext: Option[Any])
