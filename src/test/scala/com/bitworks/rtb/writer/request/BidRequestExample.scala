package com.bitworks.rtb.writer.request

import com.bitworks.rtb.model.request._

/**
  * Example [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object and JSON for it.
  *
  * @author Pavel Tomskikh
  */
trait BidRequestExample {
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

  val dealJson =
    s"""{
        |"id": "${deal.id}",
        |"bidfloor": ${deal.bidFloor},
        |"bidfloorcur": "${deal.bidFloorCur}",
        |"at": ${deal.at.get},
        |"wseat": ["${deal.wseat.get.head}"],
        |"wadomain": ["${deal.wadomain.get.head}"]
        | }""".stripMargin
  val pmpJson =
    s"""{
        |"privateauction": ${pmp.privateAuction.get},
        |"deals": [$dealJson]
        | }""".stripMargin
  val regsJson = s"""{"coppa": ${regs.coppa.get}}"""
  val segmentJson =
    s"""{
        |"id": "${segment.id.get}",
        |"name": "${segment.name.get}",
        |"value": "${segment.value.get}"
        | }""".stripMargin
  val dataJson =
    s"""{
        |"id": "${data.id.get}",
        |"name": "${data.name.get}",
        |"segment": [$segmentJson]
        | }""".stripMargin
  val geoJson =
    s"""{
        |"lat": ${geo.lat.get},
        |"lon": ${geo.lon.get},
        |"type": ${geo.`type`.get},
        |"country": "${geo.country.get}",
        |"region": "${geo.region.get}",
        |"regionfips104": "${geo.regionFips104.get}",
        |"metro": "${geo.metro.get}",
        |"city": "${geo.city.get}",
        |"zip": "${geo.zip.get}",
        |"utcoffset": ${geo.utcOffset.get}
        | }""".stripMargin
  val userJson =
    s"""{
        |"id": "${user.id.get}",
        |"buyerid": "${user.buyerId.get}",
        |"yob": ${user.yob.get},
        |"gender": "${user.gender.get}",
        |"keywords": "${user.keywords.get}",
        |"customdata": "${user.customData.get}",
        |"geo": $geoJson,
        |"data": [$dataJson]
        | }""".stripMargin
  val deviceJson =
    s"""{
        |"ua": "${device.ua.get}",
        |"geo": $geoJson,
        |"dnt": ${device.dnt.get},
        |"lmt": ${device.lmt.get},
        |"ip": "${device.ip.get}",
        |"ipv6": "${device.ipv6.get}",
        |"devicetype": ${device.deviceType.get},
        |"make": "${device.make.get}",
        |"model": "${device.model.get}",
        |"os": "${device.os.get}",
        |"osv": "${device.osv.get}",
        |"hwv": "${device.hwv.get}",
        |"h": ${device.h.get},
        |"w": ${device.w.get},
        |"ppi": ${device.ppi.get},
        |"pxratio": ${device.pxRatio.get},
        |"js": ${device.js.get},
        |"flashver": "${device.flashVer.get}",
        |"language": "${device.language.get}",
        |"carrier": "${device.carrier.get}",
        |"connectiontype": ${device.connectionType.get},
        |"ifa": "${device.ifa.get}",
        |"didsha1": "${device.didsha1.get}",
        |"didmd5": "${device.didmd5.get}",
        |"dpidsha1": "${device.dpidsha1.get}",
        |"dpidmd5": "${device.dpidmd5.get}",
        |"macsha1": "${device.macsha1.get}",
        |"macmd5": "${device.macmd5.get}"
        | }""".stripMargin
  val producerJson =
    s"""{
        |"id": "${producer.id.get}",
        |"name": "${producer.name.get}",
        |"cat": ["${producer.cat.get.head}"],
        |"domain": "${producer.domain.get}"
        | }""".stripMargin
  val publisherJson =
    s"""{
        |"id": "${publisher.id.get}",
        |"name": "${publisher.name.get}",
        |"cat": ["${publisher.cat.get.head}"],
        |"domain": "${publisher.domain.get}"
        | }""".stripMargin
  val contentJson =
    s"""{
        |"id": "${content.id.get}",
        |"episode": ${content.episode.get},
        |"title": "${content.title.get}",
        |"series": "${content.series.get}",
        |"season": "${content.season.get}",
        |"producer": $producerJson,
        |"url": "${content.url.get}",
        |"cat": ["${content.cat.get.head}", "${content.cat.get(1)}"],
        |"videoquality": ${content.videoQuality.get},
        |"context": ${content.context.get},
        |"contentrating": "${content.contentRating.get}",
        |"userrating": "${content.userRating.get}",
        |"qagmediarating": ${content.qagMediaRating.get},
        |"keywords": "${content.keywords.get}",
        |"livestream": ${content.liveStream.get},
        |"sourcerelationship": ${content.sourceRelationship.get},
        |"len": ${content.len.get},
        |"language": "${content.language.get}",
        |"embeddable": ${content.embeddable.get}
        | }""".stripMargin
  val appJson =
    s"""{
        |"id": "${app.id.get}",
        |"name": "${app.name.get}",
        |"bundle": "${app.bundle.get}",
        |"domain": "${app.domain.get}",
        |"storeurl": "${app.storeUrl.get}",
        |"cat": ["${app.cat.get.head}"],
        |"sectioncat": ["${app.sectionCat.get.head}"],
        |"pagecat": ["${app.pageCat.get.head}"],
        |"ver": "${app.ver.get}",
        |"privacypolicy": ${app.privacyPolicy.get},
        |"paid": ${app.paid.get},
        |"publisher": $publisherJson,
        |"content": $contentJson,
        |"keywords": "${app.keywords.get}"
        | }""".stripMargin
  val siteJson =
    s"""{
        |"id": "${site.id.get}",
        |"name": "${site.name.get}",
        |"domain": "${site.domain.get}",
        |"cat": ["${site.cat.get.head}"],
        |"sectioncat": ["${site.sectionCat.get.head}"],
        |"pagecat": ["${site.pageCat.get.head}"],
        |"page": "${site.page.get}",
        |"ref": "${site.ref.get}",
        |"search": "${site.search.get}",
        |"mobile": ${site.mobile.get},
        |"privacypolicy": ${site.privacyPolicy.get},
        |"publisher": $publisherJson,
        |"content": $contentJson,
        |"keywords": "${site.keywords.get}"
        | }""".stripMargin
  val nativeJson =
    s"""{
        |"request": "${native.request}",
        |"ver": "${native.ver.get}",
        |"api": [${native.api.get.head}],
        |"battr": [${native.battr.get.head}]
        | }""".stripMargin
  val bannerJson =
    s"""{
        |"w": ${banner.w.get},
        |"h": ${banner.h.get},
        |"wmax": ${banner.wmax.get},
        |"hmax": ${banner.hmax.get},
        |"wmin": ${banner.wmin.get},
        |"hmin": ${banner.hmin.get},
        |"id": "${banner.id.get}",
        |"btype": [${banner.btype.get.head}],
        |"battr": [${banner.battr.get.head}],
        |"pos": ${banner.pos.get},
        |"mimes": ["${banner.mimes.get.head}"],
        |"topframe": ${banner.topFrame.get},
        |"expdir": [${banner.expdir.get.head}],
        |"api": [${banner.api.get.head}]
        | }""".stripMargin
  val videoJson =
    s"""{
        |"mimes": ["${video.mimes.head}"],
        |"minduration": ${video.minDuration.get},
        |"maxduration": ${video.maxDuration.get},
        |"protocol": ${video.protocol.get},
        |"protocols": [${video.protocols.get.head}],
        |"w": ${video.w.get},
        |"h": ${video.h.get},
        |"startdelay": ${video.startDelay.get},
        |"linearity": ${video.linearity.get},
        |"sequence": ${video.sequence.get},
        |"battr": [${video.battr.get.head}],
        |"maxextended": ${video.maxExtended.get},
        |"minbitrate": ${video.minBitrate.get},
        |"maxbitrate": ${video.maxBitrate.get},
        |"boxingallowed": ${video.boxingAllowed},
        |"playbackmethod": [${video.playbackMethod.get.head}],
        |"delivery": [${video.delivery.get.head}],
        |"pos": ${video.pos.get},
        |"companionad": [$bannerJson],
        |"api": [${video.api.get.head}],
        |"companiontype": [${video.companionType.get.head}]
        | }""".stripMargin
  val impJson =
    s"""{
        |"id": "${imp.id}",
        |"banner": $bannerJson,
        |"video": $videoJson,
        |"native": $nativeJson,
        |"displaymanager": "${imp.displayManager.get}",
        |"displaymanagerver": "${imp.displayManagerVer.get}",
        |"instl": ${imp.instl},
        |"tagid": "${imp.tagId.get}",
        |"bidfloor": ${imp.bidFloor},
        |"bidfloorcur": "${imp.bidFloorCur}",
        |"secure": ${imp.secure.get},
        |"iframebuster": ["${imp.iframeBuster.get.head}"],
        |"pmp": $pmpJson
        | }""".stripMargin
  val bidRequestJson =
    s"""{
        |"id": "${bidRequest.id}",
        |"imp": [$impJson],
        |"site": $siteJson,
        |"app": $appJson,
        |"device": $deviceJson,
        |"user": $userJson,
        |"test": ${bidRequest.test},
        |"at": ${bidRequest.at},
        |"tmax": ${bidRequest.tmax.get},
        |"wseat": ["${bidRequest.wseat.get.head}"],
        |"allimps": ${bidRequest.allImps},
        |"cur": ["${bidRequest.cur.get.head}"],
        |"bcat": ["${bidRequest.bcat.get.head}"],
        |"badv": ["${bidRequest.badv.get.head}"],
        |"regs": $regsJson
        | }""".stripMargin
}
