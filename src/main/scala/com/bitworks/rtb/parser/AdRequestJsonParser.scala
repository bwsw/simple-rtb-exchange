package com.bitworks.rtb.parser

import com.bitworks.rtb.model.ad.request.builder.{AdRequestBuilder, ImpBuilder, SiteBuilder,
AppBuilder, UserBuilder}
import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.request.builder.{ProducerBuilder, GeoBuilder, NativeBuilder,
VideoBuilder, BannerBuilder, ContentBuilder, RegsBuilder, DeviceBuilder}
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

import scala.collection.JavaConverters._

/**
  * Json parser for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @author Egor Ilchenko
  */
class AdRequestJsonParser extends AdRequestParser with JsonParseHelper {

  override def parseInternal(bytes: Array[Byte]): AdRequest = {
    val mapper = new ObjectMapper()
    val rootNode = mapper.readTree(bytes)

    getAdRequest(rootNode)
  }

  private def getProducer(node: JsonNode) = {
    require(node.isObject, "producer node must be an object")
    val builder = ProducerBuilder()

    node.fields().asScala.foreach(e => e.getKey match {
      case "id" => builder.withId(e.getValue.getString)
      case "name" => builder.withName(e.getValue.getString)
      case "cat" => builder.withCat(e.getValue.getStringSeq)
      case "domain" => builder.withDomain(e.getValue.getString)
      case _ => //nothing
    })

    builder.build
  }

  private def getGeo(node: JsonNode) = {
    require(node.isObject, "geo node must be an object")
    val builder = GeoBuilder()

    node.fields().asScala.foreach(e => e.getKey match {
      case "lat" => builder.withLat(e.getValue.getFloat)
      case "lon" => builder.withLon(e.getValue.getFloat)
      case "type" => builder.withType(e.getValue.getInt)
      case "country" => builder.withCountry(e.getValue.getString)
      case "region" => builder.withRegion(e.getValue.getString)
      case "regionfips104" => builder.withRegionFips104(e.getValue.getString)
      case "metro" => builder.withMetro(e.getValue.getString)
      case "city" => builder.withCity(e.getValue.getString)
      case "zip" => builder.withZip(e.getValue.getString)
      case "utcoffset" => builder.withUtcOffset(e.getValue.getInt)
      case _ => //nothing
    })

    builder.build
  }

  private def getAdRequest(node: JsonNode) = {
    require(node.isObject, "adRequest node must be an object")
    val imps = node.getChild("imp").getSeq(getImp)
    val builder = AdRequestBuilder(imps)

    node.fields().asScala.foreach(e => e.getKey match {
      case "device" => builder.withDevice(getDevice(e.getValue))
      case "site" => builder.withSite(getSite(e.getValue))
      case "app" => builder.withApp(getApp(e.getValue))
      case "user" => builder.withUser(getUser(e.getValue))
      case "test" => builder.withTest(e.getValue.getInt)
      case "tmax" => builder.withTmax(e.getValue.getInt)
      case "regs" => builder.withRegs(getRegs(e.getValue))
      case _ => //nothing
    })

    builder.build
  }

  private def getImp(node: JsonNode) = {
    require(node.isObject, "imp node must be an object")
    val id = node.getChild("id").getString
    val builder = ImpBuilder(id)

    node.fields().asScala.foreach(e => e.getKey match {
      case "banner" => builder.withBanner(getBanner(e.getValue))
      case "video" => builder.withVideo(getVideo(e.getValue))
      case "native" => builder.withNative(getNative(e.getValue))
      case _ => //nothing
    })

    builder.build
  }

  private def getNative(node: JsonNode) = {
    require(node.isObject, "native node must be an object")
    val request = node.getChild("request").getString
    val builder = NativeBuilder(request)

    node.fields().asScala.foreach(e => e.getKey match {
      case "ver" => builder.withVer(e.getValue.getString)
      case "api" => builder.withApi(e.getValue.getIntSeq)
      case "battr" => builder.withBattr(e.getValue.getIntSeq)
      case _ => //nothing
    })

    builder.build
  }

  private def getVideo(node: JsonNode) = {
    require(node.isObject, "video node must be an object")
    val mimes = node.getChild("mimes").getStringSeq
    val builder = VideoBuilder(mimes)

    node.fields().asScala.foreach(e => e.getKey match {
      case "minduration" => builder.withMinDuration(e.getValue.getInt)
      case "maxduration" => builder.withMaxDuration(e.getValue.getInt)
      case "protocol" => builder.withProtocol(e.getValue.getInt)
      case "protocols" => builder.withProtocols(e.getValue.getIntSeq)
      case "w" => builder.withW(e.getValue.getInt)
      case "h" => builder.withH(e.getValue.getInt)
      case "startdelay" => builder.withStartDelay(e.getValue.getInt)
      case "linearity" => builder.withLinearity(e.getValue.getInt)
      case "sequence" => builder.withSequence(e.getValue.getInt)
      case "battr" => builder.withBattr(e.getValue.getIntSeq)
      case "maxextended" => builder.withMaxExtended(e.getValue.getInt)
      case "minbitrate" => builder.withMinBitrate(e.getValue.getInt)
      case "maxbitrate" => builder.withMaxBitrate(e.getValue.getInt)
      case "boxingallowed" => builder.withBoxingAllowed(e.getValue.getInt)
      case "playbackmethod" => builder.withPlaybackMethod(e.getValue.getIntSeq)
      case "delivery" => builder.withDelivery(e.getValue.getIntSeq)
      case "pos" => builder.withPos(e.getValue.getInt)
      case "companionad" => builder.withCompanionAd(e.getValue.getSeq(getBanner))
      case "api" => builder.withApi(e.getValue.getIntSeq)
      case "companiontype" => builder.withCompanionType(e.getValue.getIntSeq)
      case _ => //nothing
    })

    builder.build
  }

  private def getBanner(node: JsonNode) = {
    require(node.isObject, "banner node must be an object")
    val builder = BannerBuilder()

    node.fields().asScala.foreach(e => e.getKey match {
      case "w" => builder.withW(e.getValue.getInt)
      case "h" => builder.withH(e.getValue.getInt)
      case "wmax" => builder.withWmax(e.getValue.getInt)
      case "hmax" => builder.withHmax(e.getValue.getInt)
      case "wmin" => builder.withWmin(e.getValue.getInt)
      case "hmin" => builder.withHmin(e.getValue.getInt)
      case "id" => builder.withId(e.getValue.getString)
      case "btype" => builder.withBtype(e.getValue.getIntSeq)
      case "battr" => builder.withBattr(e.getValue.getIntSeq)
      case "pos" => builder.withPos(e.getValue.getInt)
      case "mimes" => builder.withMimes(e.getValue.getStringSeq)
      case "topframe" => builder.withTopFrame(e.getValue.getInt)
      case "expdir" => builder.withExpdir(e.getValue.getIntSeq)
      case "api" => builder.withApi(e.getValue.getIntSeq)
      case _ => //nothing
    })

    builder.build
  }

  private def getSite(node: JsonNode) = {
    require(node.isObject, "site node must be an object")
    val id = node.getChild("id").getInt
    val builder = SiteBuilder(id)

    node.fields().asScala.foreach(e => e.getKey match {
      case "sectioncat" => builder.withSectionCat(e.getValue.getStringSeq)
      case "pagecat" => builder.withPageCat(e.getValue.getStringSeq)
      case "page" => builder.withPage(e.getValue.getString)
      case "ref" => builder.withRef(e.getValue.getString)
      case "search" => builder.withSearch(e.getValue.getString)
      case "mobile" => builder.withMobile(e.getValue.getInt)
      case "content" => builder.withContent(getContent(e.getValue))
      case _ => //nothing
    })

    builder.build
  }

  private def getContent(node: JsonNode) = {
    require(node.isObject, "content node must be an object")
    val builder = ContentBuilder()

    node.fields().asScala.foreach(e => e.getKey match {
      case "id" => builder.withId(e.getValue.getString)
      case "episode" => builder.withEpisode(e.getValue.getInt)
      case "title" => builder.withTitle(e.getValue.getString)
      case "series" => builder.withSeries(e.getValue.getString)
      case "season" => builder.withSeason(e.getValue.getString)
      case "producer" => builder.withProducer(getProducer(e.getValue))
      case "url" => builder.withUrl(e.getValue.getString)
      case "cat" => builder.withCat(e.getValue.getStringSeq)
      case "videoquality" => builder.withVideoQuality(e.getValue.getInt)
      case "context" => builder.withContext(e.getValue.getInt)
      case "contentrating" => builder.withContentRating(e.getValue.getString)
      case "userrating" => builder.withUserRating(e.getValue.getString)
      case "qagmediarating" => builder.withQagMediaRating(e.getValue.getInt)
      case "keywords" => builder.withKeywords(e.getValue.getString)
      case "livestream" => builder.withLiveStream(e.getValue.getInt)
      case "sourcerelationship" => builder.withSourceRelationship(e.getValue.getInt)
      case "len" => builder.withLen(e.getValue.getInt)
      case "language" => builder.withLanguage(e.getValue.getString)
      case "embeddable" => builder.withEmbeddable(e.getValue.getInt)
      case _ => //nothing
    })

    builder.build
  }

  private def getApp(node: JsonNode) = {
    require(node.isObject, "app node must be an object")
    val id = node.getChild("id").getInt
    val builder = AppBuilder(id)

    node.fields().asScala.foreach(e => e.getKey match {
      case "sectioncat" => builder.withSectionCat(e.getValue.getStringSeq)
      case "pagecat" => builder.withPageCat(e.getValue.getStringSeq)
      case "content" => builder.withContent(getContent(e.getValue))
      case _ => //nothing
    })

    builder.build
  }

  private def getUser(node: JsonNode) = {
    require(node.isObject, "user node must be an object")
    val builder = UserBuilder()

    node.fields().asScala.foreach(e => e.getKey match {
      case "id" => builder.withId(e.getValue.getString)
      case "yob" => builder.withYob(e.getValue.getInt)
      case "gender" => builder.withGender(e.getValue.getString)
      case "keywords" => builder.withKeywords(e.getValue.getString)
      case "geo" => builder.withGeo(getGeo(e.getValue))
      case _ => //nothing
    })

    builder.build
  }

  private def getRegs(node: JsonNode) = {
    require(node.isObject, "regs node must be an object")
    val builder = RegsBuilder()

    node.fields().asScala.foreach(e => e.getKey match {
      case "coppa" => builder.withCoppa(e.getValue.getInt)
      case _ => //nothing
    })

    builder.build
  }

  private def getDevice(node: JsonNode) = {
    require(node.isObject, "device node must be an object")
    val builder = DeviceBuilder()

    node.fields().asScala.foreach(e => e.getKey match {
      case "ua" => builder.withUa(e.getValue.getString)
      case "geo" => builder.withGeo(getGeo(e.getValue))
      case "dnt" => builder.withDnt(e.getValue.getInt)
      case "lmt" => builder.withLmt(e.getValue.getInt)
      case "ip" => builder.withIp(e.getValue.getString)
      case "ipv6" => builder.withIpv6(e.getValue.getString)
      case "devicetype" => builder.withDeviceType(e.getValue.getInt)
      case "make" => builder.withMake(e.getValue.getString)
      case "model" => builder.withModel(e.getValue.getString)
      case "os" => builder.withOs(e.getValue.getString)
      case "osv" => builder.withOsv(e.getValue.getString)
      case "hwv" => builder.withHwv(e.getValue.getString)
      case "h" => builder.withH(e.getValue.getInt)
      case "w" => builder.withW(e.getValue.getInt)
      case "ppi" => builder.withPpi(e.getValue.getInt)
      case "pxratio" => builder.withPxRatio(e.getValue.getDouble)
      case "js" => builder.withJs(e.getValue.getInt)
      case "flashver" => builder.withFlashVer(e.getValue.getString)
      case "language" => builder.withLanguage(e.getValue.getString)
      case "carrier" => builder.withCarrier(e.getValue.getString)
      case "connectiontype" => builder.withConnectionType(e.getValue.getInt)
      case "ifa" => builder.withIfa(e.getValue.getString)
      case "didsha1" => builder.withDidsha1(e.getValue.getString)
      case "didmd5" => builder.withDidmd5(e.getValue.getString)
      case "dpidsha1" => builder.withDpidsha1(e.getValue.getString)
      case "dpidmd5" => builder.withDpidmd5(e.getValue.getString)
      case "macsha1" => builder.withMacsha1(e.getValue.getString)
      case "macmd5" => builder.withMacmd5(e.getValue.getString)
      case _ => //nothing
    })

    builder.build
  }

}
