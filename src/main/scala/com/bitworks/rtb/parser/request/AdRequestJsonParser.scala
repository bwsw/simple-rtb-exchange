package com.bitworks.rtb.parser.request

import com.bitworks.rtb.model._
import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.parser.JsonParseHelper
import com.fasterxml.jackson.databind.node.JsonNodeType
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

/**
  * Json parser for [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]].
  *
  * @author Egor Ilchenko
  */
class AdRequestJsonParser extends AdRequestParser with JsonParseHelper {

  override def parseInternal(s: String): AdRequest = {
    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    val rootNode = mapper.readValue(s, classOf[JsonNode])

    getAdRequest(rootNode)
  }

  private def getProducer(node: JsonNode) = {
    val builder = ProducerBuilder()

    node.getFields.foreach {
      case ("id", JsonNodeType.STRING, v) => builder.withId(v.asText)
      case ("name", JsonNodeType.STRING, v) => builder.withName(v.asText)
      case ("cat", JsonNodeType.ARRAY, v) => builder.withCat(v.asStringSeq)
      case ("domain", JsonNodeType.STRING, v) => builder.withDomain(v.asText)
      case ("ext", _, _) => //nothing
      case (n, t, _) => throwNotRecognized("producer", n, t)
    }

    builder.build
  }

  private def getGeo(node: JsonNode) = {
    val builder = GeoBuilder()

    node.getFields.foreach {
      case ("lat", JsonNodeType.NUMBER, v) => builder.withLat(v.asDouble.toFloat)
      case ("lon", JsonNodeType.NUMBER, v) => builder.withLon(v.asDouble.toFloat)
      case ("type", JsonNodeType.NUMBER, v) => builder.withType(v.asInt)
      case ("country", JsonNodeType.STRING, v) => builder.withCountry(v.asText)
      case ("region", JsonNodeType.STRING, v) => builder.withRegion(v.asText)
      case ("regionfips104", JsonNodeType.STRING, v) => builder.withRegionFips104(v.asText)
      case ("metro", JsonNodeType.STRING, v) => builder.withMetro(v.asText)
      case ("city", JsonNodeType.STRING, v) => builder.withCity(v.asText)
      case ("zip", JsonNodeType.STRING, v) => builder.withZip(v.asText)
      case ("utcoffset", JsonNodeType.NUMBER, v) => builder.withUtcOffset(v.asInt)
      case ("ext", _, _) => //nothing
      case (n, t, _) => throwNotRecognized("geo", n, t)
    }

    builder.build
  }

  private def getAdRequest(node: JsonNode) = {
    val imps = node.getChild("imp").asSeqUsing(getImp)
    val builder = ad.request.builder.AdRequestBuilder(imps)

    node.getFieldsWithoutIgnored(Seq("imp")).foreach {
      case ("device", JsonNodeType.OBJECT, v) => builder.withDevice(getDevice(v))
      case ("site", JsonNodeType.OBJECT, v) => builder.withSite(getSite(v))
      case ("app", JsonNodeType.OBJECT, v) => builder.withApp(getApp(v))
      case ("user", JsonNodeType.OBJECT, v) => builder.withUser(getUser(v))
      case ("test", JsonNodeType.NUMBER, v) => builder.withTest(v.asInt)
      case ("tmax", JsonNodeType.NUMBER, v) => builder.withTmax(v.asInt)
      case ("regs", JsonNodeType.OBJECT, v) => builder.withRegs(getRegs(v))
      case ("ext", _, _) =>
      case (n, t, _) => throwNotRecognized("adRequest", n, t)
    }

    builder.build
  }

  private def getImp(node: JsonNode) = {
    val id = node.getChild("id").asText
    val builder = ad.request.builder.ImpBuilder(id)

    node.getFieldsWithoutIgnored(Seq("id")).foreach {
      case ("banner", JsonNodeType.OBJECT, v) => builder.withBanner(getBanner(v))
      case ("video", JsonNodeType.OBJECT, v) => builder.withVideo(getVideo(v))
      case ("native", JsonNodeType.OBJECT, v) => builder.withNative(getNative(v))
      case (n, t, _) => throwNotRecognized("imp", n, t)
    }

    builder.build
  }

  private def getNative(node: JsonNode) = {
    val request = node.getChild("request").asText
    val builder = NativeBuilder(request)

    node.getFieldsWithoutIgnored(Seq("request")).foreach {
      case ("ver", JsonNodeType.STRING, v) => builder.withVer(v.asText)
      case ("api", JsonNodeType.ARRAY, v) => builder.withApi(v.asIntSeq)
      case ("battr", JsonNodeType.ARRAY, v) => builder.withBattr(v.asIntSeq)
      case ("ext", _, _) => //nothing
      case (n, t, _) => throwNotRecognized("native", n, t)
    }

    builder.build
  }

  private def getVideo(node: JsonNode) = {
    val mimes = node.getChild("mimes").asStringSeq
    val builder = VideoBuilder(mimes)

    node.getFieldsWithoutIgnored(Seq("mimes")).foreach {
      case ("minduration", JsonNodeType.NUMBER, v) => builder.withMinDuration(v.asInt)
      case ("maxduration", JsonNodeType.NUMBER, v) => builder.withMaxDuration(v.asInt)
      case ("protocol", JsonNodeType.NUMBER, v) => builder.withProtocol(v.asInt)
      case ("protocols", JsonNodeType.ARRAY, v) => builder.withProtocols(v.asIntSeq)
      case ("w", JsonNodeType.NUMBER, v) => builder.withW(v.asInt)
      case ("h", JsonNodeType.NUMBER, v) => builder.withH(v.asInt)
      case ("startdelay", JsonNodeType.NUMBER, v) => builder.withStartDelay(v.asInt)
      case ("linearity", JsonNodeType.NUMBER, v) => builder.withLinearity(v.asInt)
      case ("sequence", JsonNodeType.NUMBER, v) => builder.withSequence(v.asInt)
      case ("battr", JsonNodeType.ARRAY, v) => builder.withBattr(v.asIntSeq)
      case ("maxextended", JsonNodeType.NUMBER, v) => builder.withMaxExtended(v.asInt)
      case ("minbitrate", JsonNodeType.NUMBER, v) => builder.withMinBitrate(v.asInt)
      case ("maxbitrate", JsonNodeType.NUMBER, v) => builder.withMaxBitrate(v.asInt)
      case ("boxingallowed", JsonNodeType.NUMBER, v) => builder.withBoxingAllowed(v.asInt)
      case ("playbackmethod", JsonNodeType.ARRAY, v) => builder.withPlaybackMethod(v.asIntSeq)
      case ("delivery", JsonNodeType.ARRAY, v) => builder.withDelivery(v.asIntSeq)
      case ("pos", JsonNodeType.NUMBER, v) => builder.withPos(v.asInt)
      case ("companionad", JsonNodeType.ARRAY, v) => builder.withCompanionAd(v.asSeqUsing(getBanner))
      case ("api", JsonNodeType.ARRAY, v) => builder.withApi(v.asIntSeq)
      case ("companiontype", JsonNodeType.ARRAY, v) => builder.withCompanionType(v.asIntSeq)
      case ("ext", _, _) => //nothing
      case (n, t, _) => throwNotRecognized("video", n, t)
    }

    builder.build
  }

  private def getBanner(node: JsonNode) = {
    val builder = BannerBuilder()

    node.getFields.foreach {
      case ("w", JsonNodeType.NUMBER, v) => builder.withW(v.asInt)
      case ("h", JsonNodeType.NUMBER, v) => builder.withH(v.asInt)
      case ("wmax", JsonNodeType.NUMBER, v) => builder.withWmax(v.asInt)
      case ("hmax", JsonNodeType.NUMBER, v) => builder.withHmax(v.asInt)
      case ("wmin", JsonNodeType.NUMBER, v) => builder.withWmin(v.asInt)
      case ("hmin", JsonNodeType.NUMBER, v) => builder.withHmin(v.asInt)
      case ("id", JsonNodeType.STRING, v) => builder.withId(v.asText)
      case ("btype", JsonNodeType.ARRAY, v) => builder.withBtype(v.asIntSeq)
      case ("battr", JsonNodeType.ARRAY, v) => builder.withBattr(v.asIntSeq)
      case ("pos", JsonNodeType.NUMBER, v) => builder.withPos(v.asInt)
      case ("mimes", JsonNodeType.ARRAY, v) => builder.withMimes(v.asStringSeq)
      case ("topframe", JsonNodeType.NUMBER, v) => builder.withTopFrame(v.asInt)
      case ("expdir", JsonNodeType.ARRAY, v) => builder.withExpdir(v.asIntSeq)
      case ("api", JsonNodeType.ARRAY, v) => builder.withApi(v.asIntSeq)
      case ("ext", _, _) => //nothing
      case (n, t, _) => throwNotRecognized("banner", n, t)
    }

    builder.build
  }

  private def getSite(node: JsonNode) = {
    val id = node.getChild("id").asInt
    val builder = ad.request.builder.SiteBuilder(id)

    node.getFieldsWithoutIgnored(Seq("id")).foreach {
      case ("sectioncat", JsonNodeType.ARRAY, v) => builder.withSectionCat(v.asStringSeq)
      case ("pagecat", JsonNodeType.ARRAY, v) => builder.withPageCat(v.asStringSeq)
      case ("page", JsonNodeType.STRING, v) => builder.withPage(v.asText)
      case ("ref", JsonNodeType.STRING, v) => builder.withRef(v.asText)
      case ("search", JsonNodeType.STRING, v) => builder.withSearch(v.asText)
      case ("mobile", JsonNodeType.NUMBER, v) => builder.withMobile(v.asInt)
      case ("content", JsonNodeType.OBJECT, v) => builder.withContent(getContent(v))
      case (n, t, _) => throwNotRecognized("site", n, t)
    }

    builder.build
  }

  private def getContent(node: JsonNode) = {
    val builder = ContentBuilder()

    node.getFields.foreach {
      case ("id", JsonNodeType.STRING, v) => builder.withId(v.asText)
      case ("episode", JsonNodeType.NUMBER, v) => builder.withEpisode(v.asInt)
      case ("title", JsonNodeType.STRING, v) => builder.withTitle(v.asText)
      case ("series", JsonNodeType.STRING, v) => builder.withSeries(v.asText)
      case ("season", JsonNodeType.STRING, v) => builder.withSeason(v.asText)
      case ("producer", JsonNodeType.OBJECT, v) => builder.withProducer(getProducer(v))
      case ("url", JsonNodeType.STRING, v) => builder.withUrl(v.asText)
      case ("cat", JsonNodeType.ARRAY, v) => builder.withCat(v.asStringSeq)
      case ("videoquality", JsonNodeType.NUMBER, v) => builder.withVideoQuality(v.asInt)
      case ("context", JsonNodeType.NUMBER, v) => builder.withContext(v.asInt)
      case ("contentrating", JsonNodeType.STRING, v) => builder.withContentRating(v.asText)
      case ("userrating", JsonNodeType.STRING, v) => builder.withUserRating(v.asText)
      case ("qagmediarating", JsonNodeType.NUMBER, v) => builder.withQagMediaRating(v.asInt)
      case ("keywords", JsonNodeType.STRING, v) => builder.withKeywords(v.asText)
      case ("livestream", JsonNodeType.NUMBER, v) => builder.withLiveStream(v.asInt)
      case ("sourcerelationship", JsonNodeType.NUMBER, v) => builder.withSourceRelationship(v.asInt)
      case ("len", JsonNodeType.NUMBER, v) => builder.withLen(v.asInt)
      case ("language", JsonNodeType.STRING, v) => builder.withLanguage(v.asText)
      case ("embeddable", JsonNodeType.NUMBER, v) => builder.withEmbeddable(v.asInt)
      case ("ext", _, _) => //nothing
      case (n, t, _) => throwNotRecognized("content", n, t)
    }

    builder.build
  }

  private def getApp(node: JsonNode) = {
    val id = node.getChild("id").asInt
    val builder = ad.request.builder.AppBuilder(id)

    node.getFieldsWithoutIgnored(Seq("id")).foreach {
      case ("sectioncat", JsonNodeType.ARRAY, v) => builder.withSectionCat(v.asStringSeq)
      case ("pagecat", JsonNodeType.ARRAY, v) => builder.withPageCat(v.asStringSeq)
      case ("content", JsonNodeType.OBJECT, v) => builder.withContent(getContent(v))
      case (n, t, _) => throwNotRecognized("app", n, t)
    }

    builder.build
  }

  private def getUser(node: JsonNode) = {
    val builder = ad.request.builder.UserBuilder()

    node.getFields.foreach {
      case ("id", JsonNodeType.STRING, v) => builder.withId(v.asText)
      case ("yob", JsonNodeType.NUMBER, v) => builder.withYob(v.asInt)
      case ("gender", JsonNodeType.STRING, v) => builder.withGender(v.asText)
      case ("keywords", JsonNodeType.STRING, v) => builder.withKeywords(v.asText)
      case ("geo", JsonNodeType.OBJECT, v) => builder.withGeo(getGeo(v))
      case (n, t, _) => throwNotRecognized("user", n, t)
    }

    builder.build
  }

  private def getRegs(node: JsonNode) = {
    val builder = RegsBuilder()

    node.getFields.foreach {
      case ("coppa", JsonNodeType.NUMBER, v) => builder.withCoppa(v.asInt)
      case ("ext", _, _) => //nothing
      case (n, t, _) => throwNotRecognized("regs", n, t)
    }

    builder.build
  }

  private def getDevice(node: JsonNode) = {
    val builder = DeviceBuilder()

    node.getFields.foreach {
      case ("ua", JsonNodeType.STRING, v) => builder.withUa(v.asText)
      case ("geo", JsonNodeType.OBJECT, v) => builder.withGeo(getGeo(v))
      case ("dnt", JsonNodeType.NUMBER, v) => builder.withDnt(v.asInt)
      case ("lmt", JsonNodeType.NUMBER, v) => builder.withLmt(v.asInt)
      case ("ip", JsonNodeType.STRING, v) => builder.withIp(v.asText)
      case ("ipv6", JsonNodeType.STRING, v) => builder.withIpv6(v.asText)
      case ("devicetype", JsonNodeType.NUMBER, v) => builder.withDeviceType(v.asInt)
      case ("make", JsonNodeType.STRING, v) => builder.withMake(v.asText)
      case ("model", JsonNodeType.STRING, v) => builder.withModel(v.asText)
      case ("os", JsonNodeType.STRING, v) => builder.withOs(v.asText)
      case ("osv", JsonNodeType.STRING, v) => builder.withOsv(v.asText)
      case ("hwv", JsonNodeType.STRING, v) => builder.withHwv(v.asText)
      case ("h", JsonNodeType.NUMBER, v) => builder.withH(v.asInt)
      case ("w", JsonNodeType.NUMBER, v) => builder.withW(v.asInt)
      case ("ppi", JsonNodeType.NUMBER, v) => builder.withPpi(v.asInt)
      case ("pxratio", JsonNodeType.NUMBER, v) => builder.withPxRatio(v.asDouble)
      case ("js", JsonNodeType.NUMBER, v) => builder.withJs(v.asInt)
      case ("flashver", JsonNodeType.STRING, v) => builder.withFlashVer(v.asText)
      case ("language", JsonNodeType.STRING, v) => builder.withLanguage(v.asText)
      case ("carrier", JsonNodeType.STRING, v) => builder.withCarrier(v.asText)
      case ("connectiontype", JsonNodeType.NUMBER, v) => builder.withConnectionType(v.asInt)
      case ("ifa", JsonNodeType.STRING, v) => builder.withIfa(v.asText)
      case ("didsha1", JsonNodeType.STRING, v) => builder.withDidsha1(v.asText)
      case ("didmd5", JsonNodeType.STRING, v) => builder.withDidmd5(v.asText)
      case ("dpidsha1", JsonNodeType.STRING, v) => builder.withDpidsha1(v.asText)
      case ("dpidmd5", JsonNodeType.STRING, v) => builder.withDpidmd5(v.asText)
      case ("macsha1", JsonNodeType.STRING, v) => builder.withMacsha1(v.asText)
      case ("macmd5", JsonNodeType.STRING, v) => builder.withMacmd5(v.asText)
      case ("ext", _, _) => //nothing
      case (n, t, _) => throwNotRecognized("device", n, t)
    }

    builder.build
  }

}
