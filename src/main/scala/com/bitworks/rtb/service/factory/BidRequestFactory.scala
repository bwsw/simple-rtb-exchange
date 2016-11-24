package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.db.Status
import com.bitworks.rtb.model.request._
import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.model.{ad, db}
import com.bitworks.rtb.service.dao.{AppDao, CategoryDao, PublisherDao, SiteDao}

/**
  * Factory interface for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @author Egor Ilchenko
  */
trait BidRequestFactory {

  /**
    * Returns [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
    *
    * @param ad [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] object
    */
  def create(ad: AdRequest): Option[BidRequest]
}

/**
  * Factory implementation for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @author Pavel Tomskikh
  */
class BidRequestFactoryImpl(
    categoryDao: CategoryDao,
    publisherDao: PublisherDao,
    siteDao: SiteDao,
    appDao: AppDao)
  extends BidRequestFactory
    with BidRequestConstants
    with FactoryHelper {

  /**
    * Create [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object from
    * [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] object.
    *
    * @param adRequest [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] object
    * @return created Some(BidRequest) if adRequest is valid, or None if not valid
    */
  override def create(adRequest: AdRequest): Option[BidRequest] = {
    if (adRequest.id.isEmpty ||
      adRequest.imp.isEmpty ||
      (adRequest.site.isEmpty == adRequest.app.isEmpty) ||
      !isFlag(adRequest.test) ||
      !adRequest.tmax.forall(isPositive) ||
      !adRequest.device.forall(check) ||
      !adRequest.regs.forall(check) ||
      !checkImpIds(adRequest.imp)) {
      return None
    }

    val imps = adRequest.imp.map(create)
    if (!checkSeq(imps)) return None

    val builder = BidRequestBuilder(adRequest.id, imps.map(_.get))
      .withTest(adRequest.test)

    adRequest.tmax.foreach(builder.withTmax)
    adRequest.device.foreach(builder.withDevice)
    adRequest.regs.foreach(builder.withRegs)

    if (adRequest.user.nonEmpty) {
      val user = create(adRequest.user.get)
      if (user.nonEmpty) builder.withUser(user.get)
      else return None
    }

    var publisher: Option[db.Publisher] = None

    if (adRequest.site.nonEmpty) {
      val dbSite = siteDao.get(adRequest.site.get.id)
      if (dbSite.isEmpty) {
        return None
      }
      publisher = publisherDao.get(dbSite.get.publisherId)
      if (publisher.isEmpty) {
        return None
      }
      val site = create(adRequest.site.get, dbSite.get, publisher.get)
      if (site.isEmpty) {
        return None
      }
      builder.withSite(site.get)
    }
    else {
      val dbApp = appDao.get(adRequest.app.get.id)
      if (dbApp.isEmpty) {
        return None
      }
      publisher = publisherDao.get(dbApp.get.publisherId)
      if (publisher.isEmpty) {
        return None
      }
      val app = create(adRequest.app.get, dbApp.get, publisher.get)
      if (app.isEmpty) {
        return None
      }
      builder.withApp(app.get)
    }

    builder
      .withBcat(getBlockedCategories(publisher.get))
      .withBadv(publisher.get.blockedDomains)

    Some(builder.build)
  }

  private def create(adImp: ad.request.Imp): Option[Imp] = {
    if (adImp.id.isEmpty ||
      (adImp.banner.isEmpty &&
        adImp.video.isEmpty &&
        adImp.native.isEmpty) ||
      !adImp.banner.forall(check) ||
      !adImp.video.forall(check) ||
      !adImp.native.forall(check)) {
      return None
    }

    val builder = ImpBuilder(adImp.id)
    adImp.banner.foreach(builder.withBanner)
    adImp.video.foreach(builder.withVideo)
    adImp.native.foreach(builder.withNative)

    Some(builder.build)
  }

  private def create(
      adSite: ad.request.Site,
      site: db.Site,
      publisher: db.Publisher): Option[Site] = {
    if (!adSite.sectionCat.forall(checkCategories) ||
      !adSite.pageCat.forall(checkCategories) ||
      !adSite.page.forall(_.nonEmpty) ||
      !adSite.ref.forall(_.nonEmpty) ||
      !adSite.search.forall(_.nonEmpty) ||
      !adSite.mobile.forall(isFlag) ||
      !adSite.content.forall(check)) {
      return None
    }

    if (site.status != Status.active) {
      return None
    }

    val builder = SiteBuilder()
      .withId(site.id.toString)
      .withName(site.name)
      .withPublisher(create(publisher))
      .withDomain(site.domain)
      .withPrivacyPolicy(site.privacyPolicy)

    site.keyword.foreach(builder.withKeywords)
    adSite.sectionCat.foreach(builder.withSectionCat)
    adSite.pageCat.foreach(builder.withPageCat)
    adSite.page.foreach(builder.withPage)
    adSite.ref.foreach(builder.withRef)
    adSite.search.foreach(builder.withSearch)
    adSite.mobile.foreach(builder.withMobile)
    adSite.content.foreach(builder.withContent)

    val cats = getCategories(site.iabCategoriesIds)
    if (cats.nonEmpty) {
      builder.withCat(cats)
    }

    Some(builder.build)
  }

  private def create(adApp: ad.request.App, app: db.App, publisher: db.Publisher): Option[App] = {
    if (!adApp.sectionCat.forall(checkCategories) ||
      !adApp.pageCat.forall(checkCategories) ||
      !adApp.content.forall(check)) {
      return None
    }

    if (app.status != Status.active) {
      return None
    }

    val builder = AppBuilder()
      .withId(app.id.toString)
      .withName(app.name)
      .withBundle(app.bundle)
      .withStoreUrl(app.storeUrl)
      .withVer(app.version)
      .withPrivacyPolicy(app.privacyPolicy)
      .withPublisher(create(publisher))

    app.keyword.foreach(builder.withKeywords)
    app.domain.foreach(builder.withDomain)
    adApp.sectionCat.foreach(builder.withSectionCat)
    adApp.pageCat.foreach(builder.withPageCat)
    adApp.content.foreach(builder.withContent)

    val cats = getCategories(app.iabCategoriesIds)
    if (cats.nonEmpty) {
      builder.withCat(cats)
    }

    Some(builder.build)
  }

  private def create(dbPublisher: db.Publisher): Publisher = {
    val builder = PublisherBuilder()
      .withId(dbPublisher.id.toString)
      .withName(dbPublisher.name)
      .withDomain(dbPublisher.domain)

    val cats = getCategories(dbPublisher.categoriesIds)
    if (cats.nonEmpty) {
      builder.withCat(cats)
    }

    builder.build
  }

  private def create(adUser: ad.request.User): Option[User] = {
    if (!adUser.id.forall(_.nonEmpty) ||
      !adUser.yob.forall(isValidBirthdayYear) ||
      !adUser.gender.forall(genders.contains) ||
      !adUser.geo.forall(check) ||
      !adUser.keywords.forall(_.nonEmpty)) {
      return None
    }

    val builder = UserBuilder()
    adUser.id.foreach(builder.withId)
    adUser.yob.foreach(builder.withYob)
    adUser.gender.foreach(builder.withGender)
    adUser.geo.foreach(builder.withGeo)
    adUser.keywords.foreach(builder.withKeywords)
    Some(builder.build)
  }

  private def check(banner: Banner): Boolean = {
    checkSize(banner.hmin, banner.h, banner.hmax) &&
      checkSize(banner.wmin, banner.w, banner.wmax) &&
      banner.id.forall(_.nonEmpty) &&
      banner.battr.forall(check(creativeAttributes)) &&
      banner.btype.forall(check(bannerAdTypes)) &&
      banner.pos.forall(adPosition.contains) &&
      banner.mimes.forall(checkSeq(_.nonEmpty)) &&
      banner.topFrame.forall(isFlag) &&
      banner.expdir.forall(check(expandableDirection)) &&
      banner.api.forall(check(apiFrameworks))
  }

  private def check(video: Video): Boolean = {
    video.mimes.forall(_.nonEmpty) &&
      video.minDuration.forall(isPositive) &&
      video.maxDuration.forall(isPositive) &&
      notLarger(video.minDuration, video.maxDuration) &&
      video.minBitrate.forall(isPositive) &&
      video.maxBitrate.forall(isPositive) &&
      notLarger(video.minBitrate, video.maxBitrate) &&
      video.protocol.forall(videoBidResponseProtocol.contains) &&
      video.protocols.forall(check(videoBidResponseProtocol)) &&
      video.w.forall(isPositive) &&
      video.h.forall(isPositive) &&
      video.startDelay.forall(isVideoStartDelay) &&
      video.linearity.forall(videoLinearity.contains) &&
      video.battr.forall(check(creativeAttributes)) &&
      video.maxExtended.forall(_ >= -1) &&
      isFlag(video.boxingAllowed) &&
      video.playbackMethod.forall(check(videoPlaybackMethods)) &&
      video.delivery.forall(check(contentDeliveryMethods)) &&
      video.pos.forall(adPosition.contains) &&
      video.companionAd.forall(checkSeq[Banner](check)) &&
      video.api.forall(check(apiFrameworks)) &&
      video.companionType.forall(check(vastCompanionTypes))
  }

  private def check(native: Native): Boolean = {
    native.request.nonEmpty &&
      native.ver.forall(_.nonEmpty) &&
      native.api.forall(check(apiFrameworks)) &&
      native.battr.forall(check(creativeAttributes))
  }

  private def check(content: Content): Boolean = {
    content.id.forall(_.nonEmpty) &&
      content.title.forall(_.nonEmpty) &&
      content.series.forall(_.nonEmpty) &&
      content.season.forall(_.nonEmpty) &&
      content.producer.forall(check) &&
      content.url.forall(_.nonEmpty) &&
      content.cat.forall(checkCategories) &&
      content.videoQuality.forall(videoQuality.contains) &&
      content.context.forall(contentContext.contains) &&
      content.contentRating.forall(_.nonEmpty) &&
      content.userRating.forall(_.nonEmpty) &&
      content.qagMediaRating.forall(qagMediaRatings.contains) &&
      content.keywords.forall(_.nonEmpty) &&
      content.liveStream.forall(isFlag) &&
      content.sourceRelationship.forall(isFlag) &&
      content.len.forall(isPositive) &&
      content.language.forall(_.nonEmpty) &&
      content.embeddable.forall(isFlag)
  }

  private def check(producer: Producer): Boolean = {
    producer.id.forall(_.nonEmpty) &&
      producer.name.forall(_.nonEmpty) &&
      producer.cat.forall(checkCategories) &&
      producer.domain.forall(_.nonEmpty)
  }

  private def check(device: Device): Boolean = {
    device.ua.forall(_.nonEmpty) &&
      device.geo.forall(check) &&
      device.dnt.forall(isFlag) &&
      device.lmt.forall(isFlag) &&
      device.ip.forall(_.nonEmpty) &&
      device.ipv6.forall(_.nonEmpty) &&
      device.deviceType.forall(deviceType.contains) &&
      device.make.forall(_.nonEmpty) &&
      device.model.forall(_.nonEmpty) &&
      device.os.forall(_.nonEmpty) &&
      device.osv.forall(_.nonEmpty) &&
      device.hwv.forall(_.nonEmpty) &&
      device.h.forall(isPositive) &&
      device.w.forall(isPositive) &&
      device.ppi.forall(isPositive) &&
      device.pxRatio.forall(isPositive) &&
      device.js.forall(isFlag) &&
      device.flashVer.forall(_.nonEmpty) &&
      device.language.forall(_.nonEmpty) &&
      device.carrier.forall(_.nonEmpty) &&
      device.connectionType.forall(connectionType.contains) &&
      device.ifa.forall(_.nonEmpty) &&
      device.didsha1.forall(_.nonEmpty) &&
      device.didmd5.forall(_.nonEmpty) &&
      device.dpidsha1.forall(_.nonEmpty) &&
      device.dpidmd5.forall(_.nonEmpty) &&
      device.macsha1.forall(_.nonEmpty) &&
      device.macmd5.forall(_.nonEmpty)
  }

  private def check(geo: Geo): Boolean = {
    geo.lat.forall(between(-90f, 90f)) &&
      geo.lon.forall(between(-180f, 180f)) &&
      geo.`type`.forall(locationType.contains) &&
      geo.country.forall(_.nonEmpty) &&
      geo.region.forall(_.nonEmpty) &&
      geo.regionFips104.forall(_.nonEmpty) &&
      geo.metro.forall(_.nonEmpty) &&
      geo.city.forall(_.nonEmpty) &&
      geo.zip.forall(_.nonEmpty) &&
      geo.utcOffset.forall(between(-12, 14))
  }

  private def check(regs: Regs): Boolean = regs.coppa.forall(isFlag)

  private def checkCategories(s: Seq[String]): Boolean =
    s.nonEmpty && s.forall(cat => categoryDao.getAll.exists(_.iabId == cat))

  private def checkImpIds(imps: Seq[ad.request.Imp]): Boolean = {
    val ids = imps.map(_.id)
    ids.toSet.size == ids.size
  }

  private def getPublisherId(site: Site) =
    siteDao.get(site.id.get.toInt).get.publisherId

  private def getPublisherId(app: App) =
    appDao.get(app.id.get.toInt).get.publisherId

  private def getBlockedCategories(publisher: db.Publisher): Seq[String] = {
    getCategories(publisher.blockedCategoriesIds)
  }

  private def getCategories(ids: Seq[Int]): Seq[String] =
    categoryDao.get(ids).map(_.iabId)

}
