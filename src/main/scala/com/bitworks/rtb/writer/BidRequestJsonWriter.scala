package com.bitworks.rtb.writer

import com.bitworks.rtb.model.request._
import com.fasterxml.jackson.databind.node.ObjectNode

/**
  * JSON writer for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @author Pavel Tomskikh
  */
object BidRequestJsonWriter extends BidRequestWriter with JsonWriteHelper {

  /**
    * Returns JSON string, which represent [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
    *
    * @param b input [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object
    */
  def write(b: BidRequest): String =
  writeBidRequest(b).toString

  private def writeBidRequest(bidRequest: BidRequest): ObjectNode = {
    createObject
        .put("id", bidRequest.id)
        .putObjectArray("imp", bidRequest.imp, writeImp)
        .putOption("site", bidRequest.site, writeSite)
        .putOption("app", bidRequest.app, writeApp)
        .putOption("device", bidRequest.device, writeDevice)
        .putOption("user", bidRequest.user, writeUser)
        .put("test", bidRequest.test)
        .put("at", bidRequest.at)
        .putOptionInt("tmax", bidRequest.tmax)
        .putOptionStringArray("wseat", bidRequest.wseat)
        .put("allimps", bidRequest.allImps)
        .putOptionStringArray("cur", bidRequest.cur)
        .putOptionStringArray("bcat", bidRequest.bcat)
        .putOptionStringArray("badv", bidRequest.badv)
        .putOption("regs", bidRequest.regs, writeRegs)
  }

  private def writeImp(imp: Imp): ObjectNode = {
    createObject
        .put("id", imp.id)
        .putOption("banner", imp.banner, writeBanner)
        .putOption("video", imp.video, writeVideo)
        .putOption("native", imp.native, writeNative)
        .putOptionString("displaymanager", imp.displayManager)
        .putOptionString("displaymanagerver", imp.displayManagerVer)
        .put("instl", imp.instl)
        .putOptionString("tagid", imp.tagId)
        .put("bidfloor", imp.bidFloor.bigDecimal)
        .put("bidfloorcur", imp.bidFloorCur)
        .putOptionInt("secure", imp.secure)
        .putOptionStringArray("iframebuster", imp.iframeBuster)
        .putOption("pmp", imp.pmp, writePmp)
  }

  private def writeBanner(banner: Banner): ObjectNode = {
    createObject
        .putOptionInt("w", banner.w)
        .putOptionInt("h", banner.h)
        .putOptionInt("wmax", banner.wmax)
        .putOptionInt("hmax", banner.hmax)
        .putOptionInt("wmin", banner.wmin)
        .putOptionInt("hmin", banner.hmin)
        .putOptionString("id", banner.id)
        .putOptionIntArray("btype", banner.btype)
        .putOptionIntArray("battr", banner.battr)
        .putOptionInt("pos", banner.pos)
        .putOptionStringArray("mimes", banner.mimes)
        .putOptionInt("topframe", banner.topFrame)
        .putOptionIntArray("expdir", banner.expdir)
        .putOptionIntArray("api", banner.api)
  }

  private def writeVideo(video: Video): ObjectNode = {
    createObject
        .putStringArray("mimes", video.mimes)
        .putOptionInt("minduration", video.minDuration)
        .putOptionInt("maxduration", video.maxDuration)
        .putOptionInt("protocol", video.protocol)
        .putOptionIntArray("protocols", video.protocols)
        .putOptionInt("w", video.w)
        .putOptionInt("h", video.h)
        .putOptionInt("startdelay", video.startDelay)
        .putOptionInt("linearity", video.linearity)
        .putOptionInt("sequence", video.sequence)
        .putOptionIntArray("battr", video.battr)
        .putOptionInt("maxextended", video.maxExtended)
        .putOptionInt("minbitrate", video.minBitrate)
        .putOptionInt("maxbitrate", video.maxBitrate)
        .put("boxingallowed", video.boxingAllowed)
        .putOptionIntArray("playbackmethod", video.playbackMethod)
        .putOptionIntArray("delivery", video.delivery)
        .putOptionInt("pos", video.pos)
        .putOptionArray("companionad", video.companionAd, writeBanner)
        .putOptionIntArray("api", video.api)
        .putOptionIntArray("companiontype", video.companionType)
  }

  private def writeNative(native: Native): ObjectNode = {
    createObject
        .put("request", native.request)
        .putOptionString("ver", native.ver)
        .putOptionIntArray("api", native.api)
        .putOptionIntArray("battr", native.battr)
  }

  private def writeSite(site: Site): ObjectNode = {
    createObject
        .putOptionString("id", site.id)
        .putOptionString("name", site.name)
        .putOptionString("domain", site.domain)
        .putOptionStringArray("cat", site.cat)
        .putOptionStringArray("sectioncat", site.sectionCat)
        .putOptionStringArray("pagecat", site.pageCat)
        .putOptionString("page", site.page)
        .putOptionString("ref", site.ref)
        .putOptionString("search", site.search)
        .putOptionInt("mobile", site.mobile)
        .putOptionInt("privacypolicy", site.privacyPolicy)
        .putOption("publisher", site.publisher, writePublisher)
        .putOption("content", site.content, writeContent)
        .putOptionString("keywords", site.keywords)
  }

  private def writeApp(app: App): ObjectNode = {
    createObject
        .putOptionString("id", app.id)
        .putOptionString("name", app.name)
        .putOptionString("bundle", app.bundle)
        .putOptionString("domain", app.domain)
        .putOptionString("storeurl", app.storeUrl)
        .putOptionStringArray("cat", app.cat)
        .putOptionStringArray("sectioncat", app.sectionCat)
        .putOptionStringArray("pagecat", app.pageCat)
        .putOptionString("ver", app.ver)
        .putOptionInt("privacypolicy", app.privacyPolicy)
        .putOptionInt("paid", app.paid)
        .putOption("publisher", app.publisher, writePublisher)
        .putOption("content", app.content, writeContent)
        .putOptionString("keywords", app.keywords)
  }

  private def writePublisher(publisher: Publisher): ObjectNode = {
    createObject
        .putOptionString("id", publisher.id)
        .putOptionString("name", publisher.name)
        .putOptionStringArray("cat", publisher.cat)
        .putOptionString("domain", publisher.domain)
  }

  private def writeContent(content: Content): ObjectNode = {
    createObject
        .putOptionString("id", content.id)
        .putOptionInt("episode", content.episode)
        .putOptionString("title", content.title)
        .putOptionString("series", content.series)
        .putOptionString("season", content.season)
        .putOption("producer", content.producer, writeProducer)
        .putOptionString("url", content.url)
        .putOptionStringArray("cat", content.cat)
        .putOptionInt("videoquality", content.videoQuality)
        .putOptionInt("context", content.context)
        .putOptionString("contentrating", content.contentRating)
        .putOptionString("userrating", content.userRating)
        .putOptionInt("qagmediarating", content.qagMediaRating)
        .putOptionString("keywords", content.keywords)
        .putOptionInt("livestream", content.liveStream)
        .putOptionInt("sourcerelationship", content.sourceRelationship)
        .putOptionInt("len", content.len)
        .putOptionString("language", content.language)
        .putOptionInt("embeddable", content.embeddable)
  }

  private def writeProducer(producer: Producer): ObjectNode = {
    createObject
        .putOptionString("id", producer.id)
        .putOptionString("name", producer.name)
        .putOptionStringArray("cat", producer.cat)
        .putOptionString("domain", producer.domain)
  }

  private def writeDevice(device: Device): ObjectNode = {
    createObject
        .putOptionString("ua", device.ua)
        .putOption("geo", device.geo, writeGeo)
        .putOptionInt("dnt", device.dnt)
        .putOptionInt("lmt", device.lmt)
        .putOptionString("ip", device.ip)
        .putOptionString("ipv6", device.ipv6)
        .putOptionInt("devicetype", device.deviceType)
        .putOptionString("make", device.make)
        .putOptionString("model", device.model)
        .putOptionString("os", device.os)
        .putOptionString("osv", device.osv)
        .putOptionString("hwv", device.hwv)
        .putOptionInt("h", device.h)
        .putOptionInt("w", device.w)
        .putOptionInt("ppi", device.ppi)
        .putOptionDouble("pxratio", device.pxRatio)
        .putOptionInt("js", device.js)
        .putOptionString("flashver", device.flashVer)
        .putOptionString("language", device.language)
        .putOptionString("carrier", device.carrier)
        .putOptionInt("connectiontype", device.connectionType)
        .putOptionString("ifa", device.ifa)
        .putOptionString("didsha1", device.didsha1)
        .putOptionString("didmd5", device.didmd5)
        .putOptionString("dpidsha1", device.dpidsha1)
        .putOptionString("dpidmd5", device.dpidmd5)
        .putOptionString("macsha1", device.macsha1)
        .putOptionString("macmd5", device.macmd5)
  }

  private def writeGeo(geo: Geo): ObjectNode = {
    createObject
        .putOptionFloat("lat", geo.lat)
        .putOptionFloat("lon", geo.lon)
        .putOptionInt("type", geo.`type`)
        .putOptionString("country", geo.country)
        .putOptionString("region", geo.region)
        .putOptionString("regionfips104", geo.regionFips104)
        .putOptionString("metro", geo.metro)
        .putOptionString("city", geo.city)
        .putOptionString("zip", geo.zip)
        .putOptionInt("utcoffset", geo.utcOffset)
  }

  private def writeUser(user: User): ObjectNode = {
    createObject
        .putOptionString("id", user.id)
        .putOptionString("buyerid", user.buyerId)
        .putOptionInt("yob", user.yob)
        .putOptionString("gender", user.gender)
        .putOptionString("keywords", user.keywords)
        .putOptionString("customdata", user.customData)
        .putOption("geo", user.geo, writeGeo)
        .putOptionArray("data", user.data, writeData)
  }

  private def writeData(data: Data): ObjectNode = {
    createObject
        .putOptionString("id", data.id)
        .putOptionString("name", data.name)
        .putOptionArray("segment", data.segment, writeSegment)
  }

  private def writeSegment(segment: Segment): ObjectNode = {
    createObject
        .putOptionString("id", segment.id)
        .putOptionString("name", segment.name)
        .putOptionString("value", segment.value)
  }

  private def writeRegs(regs: Regs): ObjectNode =
    createObject.putOptionInt("coppa", regs.coppa)


  private def writePmp(pmp: Pmp): ObjectNode = {
    createObject
        .putOptionInt("privateauction", pmp.privateAuction)
        .putOptionArray("deals", pmp.deals, writeDeal)
  }

  private def writeDeal(deal: Deal): ObjectNode = {
    createObject
        .put("id", deal.id)
        .put("bidfloor", deal.bidFloor.bigDecimal)
        .put("bidfloorcur", deal.bidFloorCur)
        .putOptionInt("at", deal.at)
        .putOptionStringArray("wseat", deal.wseat)
        .putOptionStringArray("wadomain", deal.wadomain)
  }
}
