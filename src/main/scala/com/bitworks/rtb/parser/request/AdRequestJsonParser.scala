package com.bitworks.rtb.parser.request

import com.bitworks.rtb.model._
import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.parser.JsonParseHelper
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

/**
  * Json parser for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @author Egor Ilchenko
  */
class AdRequestJsonParser extends AdRequestParser with JsonParseHelper {

  override def parseInternal(s: String): AdRequest = {
    val mapper = new ObjectMapper()
    val rootNode = mapper.readValue(s, classOf[JsonNode])

    getAdRequest(rootNode)
  }

  private def getProducer(node: JsonNode) = {
    require(node.isObject, "producer node must be an object")
    val builder = ProducerBuilder()

    node.getFields.foreach {
      case ("id", v) => builder.withId(v.getString)
      case ("name", v) => builder.withName(v.getString)
      case ("cat", v) => builder.withCat(v.getStringSeq)
      case ("domain", v) => builder.withDomain(v.getString)
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

  private def getGeo(node: JsonNode) = {
    require(node.isObject, "geo node must be an object")
    val builder = GeoBuilder()

    node.getFields.foreach {
      case ("lat", v) => builder.withLat(v.getFloat)
      case ("lon", v) => builder.withLon(v.getFloat)
      case ("type", v) => builder.withType(v.getInt)
      case ("country", v) => builder.withCountry(v.getString)
      case ("region", v) => builder.withRegion(v.getString)
      case ("regionfips104", v) => builder.withRegionFips104(v.getString)
      case ("metro", v) => builder.withMetro(v.getString)
      case ("city", v) => builder.withCity(v.getString)
      case ("zip", v) => builder.withZip(v.getString)
      case ("utcoffset", v) => builder.withUtcOffset(v.getInt)
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

  private def getAdRequest(node: JsonNode) = {
    require(node.isObject, "adRequest node must be an object")
    val imps = node.getChild("imp").getSeqUsing(getImp)
    val builder = ad.request.builder.AdRequestBuilder(imps)

    node.getFieldsWithoutIgnored(Seq("imp")).foreach {
      case ("device", v) => builder.withDevice(getDevice(v))
      case ("site", v) => builder.withSite(getSite(v))
      case ("app", v) => builder.withApp(getApp(v))
      case ("user", v) => builder.withUser(getUser(v))
      case ("test", v) => builder.withTest(v.getInt)
      case ("tmax", v) => builder.withTmax(v.getInt)
      case ("regs", v) => builder.withRegs(getRegs(v))
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

  private def getImp(node: JsonNode) = {
    require(node.isObject, "imp node must be an object")
    val id = node.getChild("id").getString
    val builder = ad.request.builder.ImpBuilder(id)

    node.getFieldsWithoutIgnored(Seq("id")).foreach {
      case ("banner", v) => builder.withBanner(getBanner(v))
      case ("video", v) => builder.withVideo(getVideo(v))
      case ("native", v) => builder.withNative(getNative(v))
      case _ => //nothing
    }

    builder.build
  }

  private def getNative(node: JsonNode) = {
    require(node.isObject, "native node must be an object")
    val request = node.getChild("request").getString
    val builder = NativeBuilder(request)

    node.getFieldsWithoutIgnored(Seq("request")).foreach {
      case ("ver", v) => builder.withVer(v.getString)
      case ("api", v) => builder.withApi(v.getIntSeq)
      case ("battr", v) => builder.withBattr(v.getIntSeq)
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

  private def getVideo(node: JsonNode) = {
    require(node.isObject, "video node must be an object")
    val mimes = node.getChild("mimes").getStringSeq
    val builder = VideoBuilder(mimes)

    node.getFieldsWithoutIgnored(Seq("mimes")).foreach {
      case ("minduration", v) => builder.withMinDuration(v.getInt)
      case ("maxduration", v) => builder.withMaxDuration(v.getInt)
      case ("protocol", v) => builder.withProtocol(v.getInt)
      case ("protocols", v) => builder.withProtocols(v.getIntSeq)
      case ("w", v) => builder.withW(v.getInt)
      case ("h", v) => builder.withH(v.getInt)
      case ("startdelay", v) => builder.withStartDelay(v.getInt)
      case ("linearity", v) => builder.withLinearity(v.getInt)
      case ("sequence", v) => builder.withSequence(v.getInt)
      case ("battr", v) => builder.withBattr(v.getIntSeq)
      case ("maxextended", v) => builder.withMaxExtended(v.getInt)
      case ("minbitrate", v) => builder.withMinBitrate(v.getInt)
      case ("maxbitrate", v) => builder.withMaxBitrate(v.getInt)
      case ("boxingallowed", v) => builder.withBoxingAllowed(v.getInt)
      case ("playbackmethod", v) => builder.withPlaybackMethod(v.getIntSeq)
      case ("delivery", v) => builder.withDelivery(v.getIntSeq)
      case ("pos", v) => builder.withPos(v.getInt)
      case ("companionad", v) => builder.withCompanionAd(v.getSeqUsing(getBanner))
      case ("api", v) => builder.withApi(v.getIntSeq)
      case ("companiontype", v) => builder.withCompanionType(v.getIntSeq)
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

  private def getBanner(node: JsonNode) = {
    require(node.isObject, "banner node must be an object")
    val builder = BannerBuilder()

    node.getFields.foreach {
      case ("w", v) => builder.withW(v.getInt)
      case ("h", v) => builder.withH(v.getInt)
      case ("wmax", v) => builder.withWmax(v.getInt)
      case ("hmax", v) => builder.withHmax(v.getInt)
      case ("wmin", v) => builder.withWmin(v.getInt)
      case ("hmin", v) => builder.withHmin(v.getInt)
      case ("id", v) => builder.withId(v.getString)
      case ("btype", v) => builder.withBtype(v.getIntSeq)
      case ("battr", v) => builder.withBattr(v.getIntSeq)
      case ("pos", v) => builder.withPos(v.getInt)
      case ("mimes", v) => builder.withMimes(v.getStringSeq)
      case ("topframe", v) => builder.withTopFrame(v.getInt)
      case ("expdir", v) => builder.withExpdir(v.getIntSeq)
      case ("api", v) => builder.withApi(v.getIntSeq)
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

  private def getSite(node: JsonNode) = {
    require(node.isObject, "site node must be an object")
    val id = node.getChild("id").getInt
    val builder = ad.request.builder.SiteBuilder(id)

    node.getFieldsWithoutIgnored(Seq("id")).foreach {
      case ("sectioncat", v) => builder.withSectionCat(v.getStringSeq)
      case ("pagecat", v) => builder.withPageCat(v.getStringSeq)
      case ("page", v) => builder.withPage(v.getString)
      case ("ref", v) => builder.withRef(v.getString)
      case ("search", v) => builder.withSearch(v.getString)
      case ("mobile", v) => builder.withMobile(v.getInt)
      case ("content", v) => builder.withContent(getContent(v))
      case _ => //nothing
    }

    builder.build
  }

  private def getContent(node: JsonNode) = {
    require(node.isObject, "content node must be an object")
    val builder = ContentBuilder()

    node.getFields.foreach {
      case ("id", v) => builder.withId(v.getString)
      case ("episode", v) => builder.withEpisode(v.getInt)
      case ("title", v) => builder.withTitle(v.getString)
      case ("series", v) => builder.withSeries(v.getString)
      case ("season", v) => builder.withSeason(v.getString)
      case ("producer", v) => builder.withProducer(getProducer(v))
      case ("url", v) => builder.withUrl(v.getString)
      case ("cat", v) => builder.withCat(v.getStringSeq)
      case ("videoquality", v) => builder.withVideoQuality(v.getInt)
      case ("context", v) => builder.withContext(v.getInt)
      case ("contentrating", v) => builder.withContentRating(v.getString)
      case ("userrating", v) => builder.withUserRating(v.getString)
      case ("qagmediarating", v) => builder.withQagMediaRating(v.getInt)
      case ("keywords", v) => builder.withKeywords(v.getString)
      case ("livestream", v) => builder.withLiveStream(v.getInt)
      case ("sourcerelationship", v) => builder.withSourceRelationship(v.getInt)
      case ("len", v) => builder.withLen(v.getInt)
      case ("language", v) => builder.withLanguage(v.getString)
      case ("embeddable", v) => builder.withEmbeddable(v.getInt)
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

  private def getApp(node: JsonNode) = {
    require(node.isObject, "app node must be an object")
    val id = node.getChild("id").getInt
    val builder = ad.request.builder.AppBuilder(id)

    node.getFieldsWithoutIgnored(Seq("id")).foreach {
      case ("sectioncat", v) => builder.withSectionCat(v.getStringSeq)
      case ("pagecat", v) => builder.withPageCat(v.getStringSeq)
      case ("content", v) => builder.withContent(getContent(v))
      case _ => //nothing
    }

    builder.build
  }

  private def getUser(node: JsonNode) = {
    require(node.isObject, "user node must be an object")
    val builder = ad.request.builder.UserBuilder()

    node.getFields.foreach {
      case ("id", v) => builder.withId(v.getString)
      case ("yob", v) => builder.withYob(v.getInt)
      case ("gender", v) => builder.withGender(v.getString)
      case ("keywords", v) => builder.withKeywords(v.getString)
      case ("geo", v) => builder.withGeo(getGeo(v))
      case _ => //nothing
    }

    builder.build
  }

  private def getRegs(node: JsonNode) = {
    require(node.isObject, "regs node must be an object")
    val builder = RegsBuilder()

    node.getFields.foreach {
      case ("coppa", v) => builder.withCoppa(v.getInt)
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

  private def getDevice(node: JsonNode) = {
    require(node.isObject, "device node must be an object")
    val builder = DeviceBuilder()

    node.getFields.foreach {
      case ("ua", v) => builder.withUa(v.getString)
      case ("geo", v) => builder.withGeo(getGeo(v))
      case ("dnt", v) => builder.withDnt(v.getInt)
      case ("lmt", v) => builder.withLmt(v.getInt)
      case ("ip", v) => builder.withIp(v.getString)
      case ("ipv6", v) => builder.withIpv6(v.getString)
      case ("devicetype", v) => builder.withDeviceType(v.getInt)
      case ("make", v) => builder.withMake(v.getString)
      case ("model", v) => builder.withModel(v.getString)
      case ("os", v) => builder.withOs(v.getString)
      case ("osv", v) => builder.withOsv(v.getString)
      case ("hwv", v) => builder.withHwv(v.getString)
      case ("h", v) => builder.withH(v.getInt)
      case ("w", v) => builder.withW(v.getInt)
      case ("ppi", v) => builder.withPpi(v.getInt)
      case ("pxratio", v) => builder.withPxRatio(v.getDouble)
      case ("js", v) => builder.withJs(v.getInt)
      case ("flashver", v) => builder.withFlashVer(v.getString)
      case ("language", v) => builder.withLanguage(v.getString)
      case ("carrier", v) => builder.withCarrier(v.getString)
      case ("connectiontype", v) => builder.withConnectionType(v.getInt)
      case ("ifa", v) => builder.withIfa(v.getString)
      case ("didsha1", v) => builder.withDidsha1(v.getString)
      case ("didmd5", v) => builder.withDidmd5(v.getString)
      case ("dpidsha1", v) => builder.withDpidsha1(v.getString)
      case ("dpidmd5", v) => builder.withDpidmd5(v.getString)
      case ("macsha1", v) => builder.withMacsha1(v.getString)
      case ("macmd5", v) => builder.withMacmd5(v.getString)
      case ("ext", _) => //nothing
      case _ => //nothing
    }

    builder.build
  }

}
