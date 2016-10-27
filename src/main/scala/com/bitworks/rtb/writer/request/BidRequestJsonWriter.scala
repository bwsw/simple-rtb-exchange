package com.bitworks.rtb.writer.request

import com.bitworks.rtb.model.request._
import com.bitworks.rtb.writer.JsonWriteHelper
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
        .putObjectArray("imp", Some(bidRequest.imp), writeImp)
        .putOption("site", bidRequest.site, writeSite)
        .putOption("app", bidRequest.app, writeApp)
        .putOption("device", bidRequest.device, writeDevice)
        .putOption("user", bidRequest.user, writeUser)
        .put("test", bidRequest.test)
        .put("at", bidRequest.at)
        .putOption("tmax", bidRequest.tmax)
        .putStringArray("wseat", bidRequest.wseat)
        .put("allimps", bidRequest.allImps)
        .putStringArray("cur", bidRequest.cur)
        .putStringArray("bcat", bidRequest.bcat)
        .putStringArray("badv", bidRequest.badv)
        .putOption("regs", bidRequest.regs, writeRegs)
  }

  private def writeImp(imp: Imp): ObjectNode = {
    createObject
        .put("id", imp.id)
        .putOption("banner", imp.banner, writeBanner)
        .putOption("video", imp.video, writeVideo)
        .putOption("native", imp.native, writeNative)
        .putOption("displaymanager", imp.displayManager)
        .putOption("displaymanagerver", imp.displayManagerVer)
        .put("instl", imp.instl)
        .putOption("tagid", imp.tagId)
        .put("bidfloor", imp.bidFloor.bigDecimal)
        .put("bidfloorcur", imp.bidFloorCur)
        .putOption("secure", imp.secure)
        .putStringArray("iframebuster", imp.iframeBuster)
        .putOption("pmp", imp.pmp, writePmp)
  }

  private def writeBanner(banner: Banner): ObjectNode = {
    createObject
        .putOption("w", banner.w)
        .putOption("h", banner.h)
        .putOption("wmax", banner.wmax)
        .putOption("hmax", banner.hmax)
        .putOption("wmin", banner.wmin)
        .putOption("hmin", banner.hmin)
        .putOption("id", banner.id)
        .putIntArray("btype", banner.btype)
        .putIntArray("battr", banner.battr)
        .putOption("pos", banner.pos)
        .putStringArray("mimes", banner.mimes)
        .putOption("topframe", banner.topFrame)
        .putIntArray("expdir", banner.expdir)
        .putIntArray("api", banner.api)
  }

  private def writeVideo(video: Video): ObjectNode = {
    createObject
        .putStringArray("mimes", Some(video.mimes))
        .putOption("minduration", video.minDuration)
        .putOption("maxduration", video.maxDuration)
        .putOption("protocol", video.protocol)
        .putIntArray("protocols", video.protocols)
        .putOption("w", video.w)
        .putOption("h", video.h)
        .putOption("startdelay", video.startDelay)
        .putOption("linearity", video.linearity)
        .putOption("sequence", video.sequence)
        .putIntArray("battr", video.battr)
        .putOption("maxextended", video.maxExtended)
        .putOption("minbitrate", video.minBitrate)
        .putOption("maxbitrate", video.maxBitrate)
        .put("boxingallowed", video.boxingAllowed)
        .putIntArray("playbackmethod", video.playbackMethod)
        .putIntArray("delivery", video.delivery)
        .putOption("pos", video.pos)
        .putObjectArray("companionad", video.companionAd, writeBanner)
        .putIntArray("api", video.api)
        .putIntArray("companiontype", video.companionType)
  }

  private def writeNative(native: Native): ObjectNode = {
    createObject
        .put("request", native.request)
        .putOption("ver", native.ver)
        .putIntArray("api", native.api)
        .putIntArray("battr", native.battr)
  }

  private def writeSite(site: Site): ObjectNode = {
    createObject
        .putOption("id", site.id)
        .putOption("name", site.name)
        .putOption("domain", site.domain)
        .putStringArray("cat", site.cat)
        .putStringArray("sectioncat", site.sectionCat)
        .putStringArray("pagecat", site.pageCat)
        .putOption("page", site.page)
        .putOption("ref", site.ref)
        .putOption("search", site.search)
        .putOption("mobile", site.mobile)
        .putOption("privacypolicy", site.privacyPolicy)
        .putOption("publisher", site.publisher, writePublisher)
        .putOption("content", site.content, writeContent)
        .putOption("keywords", site.keywords)
  }

  private def writeApp(app: App): ObjectNode = {
    createObject
        .putOption("id", app.id)
        .putOption("name", app.name)
        .putOption("bundle", app.bundle)
        .putOption("domain", app.domain)
        .putOption("storeurl", app.storeUrl)
        .putStringArray("cat", app.cat)
        .putStringArray("sectioncat", app.sectionCat)
        .putStringArray("pagecat", app.pageCat)
        .putOption("ver", app.ver)
        .putOption("privacypolicy", app.privacyPolicy)
        .putOption("paid", app.paid)
        .putOption("publisher", app.publisher, writePublisher)
        .putOption("content", app.content, writeContent)
        .putOption("keywords", app.keywords)
  }

  private def writePublisher(publisher: Publisher): ObjectNode = {
    createObject
        .putOption("id", publisher.id)
        .putOption("name", publisher.name)
        .putStringArray("cat", publisher.cat)
        .putOption("domain", publisher.domain)
  }

  private def writeContent(content: Content): ObjectNode = {
    createObject
        .putOption("id", content.id)
        .putOption("episode", content.episode)
        .putOption("title", content.title)
        .putOption("series", content.series)
        .putOption("season", content.season)
        .putOption("producer", content.producer, writeProducer)
        .putOption("url", content.url)
        .putStringArray("cat", content.cat)
        .putOption("videoquality", content.videoQuality)
        .putOption("context", content.context)
        .putOption("contentrating", content.contentRating)
        .putOption("userrating", content.userRating)
        .putOption("qagmediarating", content.qagMediaRating)
        .putOption("keywords", content.keywords)
        .putOption("livestream", content.liveStream)
        .putOption("sourcerelationship", content.sourceRelationship)
        .putOption("len", content.len)
        .putOption("language", content.language)
        .putOption("embeddable", content.embeddable)
  }

  private def writeProducer(producer: Producer): ObjectNode = {
    createObject
        .putOption("id", producer.id)
        .putOption("name", producer.name)
        .putStringArray("cat", producer.cat)
        .putOption("domain", producer.domain)
  }

  private def writeDevice(device: Device): ObjectNode = {
    createObject
        .putOption("ua", device.ua)
        .putOption("geo", device.geo, writeGeo)
        .putOption("dnt", device.dnt)
        .putOption("lmt", device.lmt)
        .putOption("ip", device.ip)
        .putOption("ipv6", device.ipv6)
        .putOption("devicetype", device.deviceType)
        .putOption("make", device.make)
        .putOption("model", device.model)
        .putOption("os", device.os)
        .putOption("osv", device.osv)
        .putOption("hwv", device.hwv)
        .putOption("h", device.h)
        .putOption("w", device.w)
        .putOption("ppi", device.ppi)
        .putOption("pxratio", device.pxRatio)
        .putOption("js", device.js)
        .putOption("flashver", device.flashVer)
        .putOption("language", device.language)
        .putOption("carrier", device.carrier)
        .putOption("connectiontype", device.connectionType)
        .putOption("ifa", device.ifa)
        .putOption("didsha1", device.didsha1)
        .putOption("didmd5", device.didmd5)
        .putOption("dpidsha1", device.dpidsha1)
        .putOption("dpidmd5", device.dpidmd5)
        .putOption("macsha1", device.macsha1)
        .putOption("macmd5", device.macmd5)
  }

  private def writeGeo(geo: Geo): ObjectNode = {
    createObject
        .putOption("lat", geo.lat)
        .putOption("lon", geo.lon)
        .putOption("type", geo.`type`)
        .putOption("country", geo.country)
        .putOption("region", geo.region)
        .putOption("regionfips104", geo.regionFips104)
        .putOption("metro", geo.metro)
        .putOption("city", geo.city)
        .putOption("zip", geo.zip)
        .putOption("utcoffset", geo.utcOffset)
  }

  private def writeUser(user: User): ObjectNode = {
    createObject
        .putOption("id", user.id)
        .putOption("buyerid", user.buyerId)
        .putOption("yob", user.yob)
        .putOption("gender", user.gender)
        .putOption("keywords", user.keywords)
        .putOption("customdata", user.customData)
        .putOption("geo", user.geo, writeGeo)
        .putObjectArray("data", user.data, writeData)
  }

  private def writeData(data: Data): ObjectNode = {
    createObject
        .putOption("id", data.id)
        .putOption("name", data.name)
        .putObjectArray("segment", data.segment, writeSegment)
  }

  private def writeSegment(segment: Segment): ObjectNode = {
    createObject
        .putOption("id", segment.id)
        .putOption("name", segment.name)
        .putOption("value", segment.value)
  }

  private def writeRegs(regs: Regs): ObjectNode =
    createObject.putOption("coppa", regs.coppa)


  private def writePmp(pmp: Pmp): ObjectNode = {
    createObject
        .putOption("privateauction", pmp.privateAuction)
        .putObjectArray("deals", pmp.deals, writeDeal)
  }

  private def writeDeal(deal: Deal): ObjectNode = {
    createObject
        .put("id", deal.id)
        .put("bidfloor", deal.bidFloor.bigDecimal)
        .put("bidfloorcur", deal.bidFloorCur)
        .putOption("at", deal.at)
        .putStringArray("wseat", deal.wseat)
        .putStringArray("wadomain", deal.wadomain)
  }
}
