package com.bitworks.rtb.service.writer

import com.bitworks.rtb.model.request._

/**
  * Example [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object.
  *
  * @author Pavel Tomskikh
  */
object BidRequestWithOption {
  val deal = Deal(
    "id",
    1,
    "EUR",
    Some(1),
    Some(Seq("wseat")),
    Some(Seq("wadomain")),
    Some("string"))
  val pmp = Pmp(Some(1), Some(Seq(deal)), Some("string"))
  val regs = Regs(Some(42), Some("ext"))
  val segment = Segment(Some("id"), Some("name"), Some("value"), Some("ext"))
  val data = Data(Some("id"), Some("name"), Some(Seq(segment)), Some("ext"))
  val geo = Geo(
    Some(42.42f),
    Some(24.24f),
    Some(1),
    Some("country"),
    Some("region"),
    Some("regionFips"),
    Some("metro"),
    Some("city"),
    Some("zip"),
    Some(14),
    Some("string"))
  val user = User(
    Some("id"),
    Some("buyerid"),
    Some(2016),
    Some("gender"),
    Some("keywords"),
    Some("customdata"),
    Some(geo),
    Some(Seq(data)),
    Some("ext"))
  val device = Device(
    Some("ua"),
    Some(geo),
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
  val producer = Producer(
    Some("123"),
    Some("prod"),
    Some(Seq("IAB1-2")),
    Some("prod.com"),
    Some("ext"))
  val publisher = Publisher(
    Some("123"),
    Some("pub"),
    Some(Seq("IAB1-2")),
    Some("pub.com"),
    Some("ext"))
  val content = Content(
    Some("123"),
    Some(5),
    Some("title"),
    Some("auto"),
    Some("s3"),
    Some(producer),
    Some("content.com"),
    Some(Seq("IAB1-3", "IAB5-7")),
    Some(2),
    Some(1),
    Some("MPAA"),
    Some("middle"),
    Some(2),
    Some("kw1,kw2"),
    Some(1),
    Some(1),
    Some(30),
    Some("ru"),
    Some(0),
    Some("ext"))
  val app = App(
    Some("123"),
    Some("prod"),
    Some("bundle"),
    Some("app.com"),
    Some("app.com/app"),
    Some(Seq("IAB1-2")),
    Some(Seq("IAB3-4")),
    Some(Seq("IAB2-7")),
    Some("1.4.2"),
    Some(1),
    Some(0),
    Some(publisher),
    Some(content),
    Some("kw1,kw2"),
    Some("ext"))
  val site = Site(
    Some("123"),
    Some("prod"),
    Some("site.com"),
    Some(Seq("IAB1-2")),
    Some(Seq("IAB3-4")),
    Some(Seq("IAB2-7")),
    Some("pg22"),
    Some("from.com"),
    Some("site"),
    Some(0),
    Some(1),
    Some(publisher),
    Some(content),
    Some("kw1,kw2"),
    Some("ext"))
  val native = Native(
    "request",
    Some("ver"),
    Some(Seq(1)),
    Some(Seq(2)),
    Some("string"))
  val banner = Banner(
    Some(1),
    Some(2),
    Some(3),
    Some(4),
    Some(5),
    Some(6),
    Some("id"),
    Some(Seq(7)),
    Some(Seq(8)),
    Some(9),
    Some(Seq("mime")),
    Some(10),
    Some(Seq(11)),
    Some(Seq(12)),
    Some("string"))
  val video = Video(
    Seq("mime"),
    Some(1),
    Some(2),
    Some(3),
    Some(Seq(4)),
    Some(5),
    Some(6),
    Some(7),
    Some(8),
    Some(9),
    Some(Seq(10)),
    Some(11),
    Some(12),
    Some(13),
    14,
    Some(Seq(15)),
    Some(Seq(16)),
    Some(17),
    Some(Seq(banner)),
    Some(Seq(18)),
    Some(Seq(19)),
    Some("string"))
  val imp = Imp(
    "id",
    Some(banner),
    Some(video),
    Some(native),
    Some("displaymanager"),
    Some("displaymanagerver"),
    1,
    Some("tagid"),
    2,
    "EUR",
    Some(2),
    Some(Seq("iframebuster")),
    Some(pmp),
    Some("ext"))
  val bidRequest = BidRequest(
    "req",
    Seq(imp),
    Some(site),
    Some(app),
    Some(device),
    Some(user),
    1,
    3,
    Some(10),
    Some(Seq("b")),
    1,
    Some(Seq("c")),
    Some(Seq("IAB3-4")),
    Some(Seq("block")),
    Some(regs),
    Some("ext"))
}
