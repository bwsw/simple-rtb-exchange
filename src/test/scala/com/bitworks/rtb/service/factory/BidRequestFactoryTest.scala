package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.response.ErrorCode
import com.bitworks.rtb.model.ad.{request => ad}
import com.bitworks.rtb.model.db
import com.bitworks.rtb.model.db.IABCategory
import com.bitworks.rtb.model.http.Json
import com.bitworks.rtb.model.request._
import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.service.DataValidationException
import com.bitworks.rtb.service.dao._
import org.easymock.EasyMock._
import org.easymock.IAnswer
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

/**
  * Test for [[com.bitworks.rtb.service.factory.BidRequestFactory BidRequestFactory]].
  *
  * @author Pavel Tomskikh
  */
class BidRequestFactoryTest
  extends FlatSpec
    with Matchers
    with EasyMockSugar
    with OneInstancePerTest
    with ValuesForBidRequestFactoryTest {
  val intCapture = newCapture[Int]
  val seqIntCapture = newCapture[Seq[Int]]

  val categoryDao = mock[CategoryDao]
  expecting {
    categoryDao.getAll.andStubReturn(iabCategories)
    categoryDao.get(capture(intCapture)).andStubAnswer(
      new IAnswer[Option[IABCategory]] {
        override def answer(): Option[IABCategory] = getCategory(intCapture.getValue)
      })
    categoryDao.get(capture(seqIntCapture)).andStubAnswer(
      new IAnswer[Seq[IABCategory]] {
        override def answer(): Seq[IABCategory] = getCategories(seqIntCapture.getValue)
      })
    replay(categoryDao)
  }

  val dbPublisher1 = db.Publisher(
    1, "pub1", Seq(1, 2, 3), "pub1.com", Seq("block1.com", "block2.com"), Seq(6, 7))
  val publisher1 = PublisherBuilder()
    .withId(dbPublisher1.id.toString)
    .withName(dbPublisher1.name)
    .withCat(getCategoriesId(dbPublisher1.categoriesIds))
    .withDomain(dbPublisher1.domain)
    .build
  val dbPublisher2 = db.Publisher(
    2, "pub2", Seq(3, 4, 5), "pub2.com", Seq("block2.com", "block3.com"), Seq(1, 2))
  val publisher2 = PublisherBuilder()
    .withId(dbPublisher2.id.toString)
    .withName(dbPublisher2.name)
    .withCat(getCategoriesId(dbPublisher2.categoriesIds))
    .withDomain(dbPublisher2.domain)
    .build
  val dbPublishers = Seq(dbPublisher1, dbPublisher2)
  val publisherDao = mock[PublisherDao]
  expecting {
    publisherDao.getAll.andStubReturn(dbPublishers)
    publisherDao.get(capture(intCapture)).andStubAnswer(
      new IAnswer[Option[db.Publisher]] {
        override def answer(): Option[db.Publisher] =
          dbPublishers.find(_.id == intCapture.getValue)
      })
    publisherDao.get(capture(seqIntCapture)).andStubAnswer(
      new IAnswer[Seq[db.Publisher]] {
        override def answer(): Seq[db.Publisher] =
          dbPublishers.filter(p => seqIntCapture.getValue.contains(p.id))
      })
    replay(publisherDao)
  }

  val dbSiteActive = db.Site(
    1, "siteActive", 1, db.Status.active, 0, test = true, "site.com", Some("kw1,kw2"), Seq(1, 2))
  val dbSiteInactive = db.Site(
    3,
    "siteInactive",
    1,
    db.Status.inactive,
    0,
    test = true,
    "site.com",
    Some("kw1,kw2"),
    Seq(1, 2))
  val adSiteBuilder = ad.builder.SiteBuilder(dbSiteActive.id)
  val siteBuilder = SiteBuilder()
    .withId(dbSiteActive.id.toString)
    .withName(dbSiteActive.name)
    .withDomain(dbSiteActive.domain)
    .withKeywords(dbSiteActive.keyword.get)
    .withCat(getCategoriesId(dbSiteActive.iabCategoriesIds))
    .withPrivacyPolicy(dbSiteActive.privacyPolicy)
  val dbSites = Seq(dbSiteActive, dbSiteInactive)
  val siteDao = mock[SiteDao]
  expecting {
    siteDao.getAll.andStubReturn(dbSites)
    siteDao.get(capture(intCapture)).andStubAnswer(
      new IAnswer[Option[db.Site]] {
        override def answer(): Option[db.Site] =
          dbSites.find(_.id == intCapture.getValue)
      })
    siteDao.get(capture(seqIntCapture)).andStubAnswer(
      new IAnswer[Seq[db.Site]] {
        override def answer(): Seq[db.Site] =
          dbSites.filter(p => seqIntCapture.getValue.contains(p.id))
      })
    replay(siteDao)
  }

  val dbAppActive = db.App(
    2,
    "appActive",
    2,
    db.Status.active,
    1,
    test = true,
    None,
    None,
    Seq(4, 5),
    "com.app",
    "store.com",
    "0.1")
  val dbAppInactive = db.App(
    4,
    "appInactive",
    2,
    db.Status.inactive,
    1,
    test = true,
    None,
    None,
    Seq(4, 5),
    "com.app",
    "store.com",
    "0.1")
  val adAppBuilder = ad.builder.AppBuilder(dbAppActive.id)
  val appBuilder = AppBuilder()
    .withId(dbAppActive.id.toString)
    .withName(dbAppActive.name)
    .withCat(getCategoriesId(dbAppActive.iabCategoriesIds))
    .withBundle(dbAppActive.bundle)
    .withVer(dbAppActive.version)
    .withStoreUrl(dbAppActive.storeUrl)
    .withPrivacyPolicy(dbAppActive.privacyPolicy)
  val dbApps = Seq(dbAppActive, dbAppInactive)
  val appDao = mock[AppDao]
  expecting {
    appDao.getAll.andStubReturn(dbApps)
    appDao.get(capture(intCapture)).andStubAnswer(
      new IAnswer[Option[db.App]] {
        override def answer(): Option[db.App] =
          dbApps.find(_.id == intCapture.getValue)
      })
    appDao.get(capture(seqIntCapture)).andStubAnswer(
      new IAnswer[Seq[db.App]] {
        override def answer(): Seq[db.App] =
          dbApps.filter(p => seqIntCapture.getValue.contains(p.id))
      })
    replay(appDao)
  }

  val adRequestId = "12345"

  val factory = new BidRequestFactoryImpl(categoryDao, publisherDao, siteDao, appDao)

  "BidRequestFactory" should "create bid request for correct ad request with site" in {
    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adSite = adSiteBuilder
      .withSectionCat(getCategoriesId(Seq(1, 2)))
      .withPageCat(getCategoriesId(Seq(1)))
      .withPage("page123")
      .withRef("from.com")
      .withSearch("search")
      .withMobile(0)
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withSite(adSite)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val site = siteBuilder.withPublisher(publisher1)
      .withSectionCat(adSite.sectionCat.get)
      .withPageCat(adSite.pageCat.get)
      .withPage(adSite.page.get)
      .withRef(adSite.ref.get)
      .withSearch(adSite.search.get)
      .withMobile(adSite.mobile.get)
      .withContent(adSite.content.get)
      .build
    val imp = ImpBuilder(adImp.id)
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .withUser(correctUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    factory.create(adRequest) shouldBe expectedBidRequest
  }

  it should "throw DataValidationException for ad request with inactive site" in {
    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adSite = ad.builder.SiteBuilder(dbSiteInactive.id)
      .withSectionCat(getCategoriesId(Seq(1, 2)))
      .withPageCat(getCategoriesId(Seq(1)))
      .withPage("page123")
      .withRef("from.com")
      .withSearch("search")
      .withMobile(0)
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withSite(adSite)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.SITE_OR_APP_INACTIVE
  }

  it should "throw DataValidationException for ad request with site which not in db" in {
    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adSite = ad.builder.SiteBuilder(100)
      .withSectionCat(getCategoriesId(Seq(1, 2)))
      .withPageCat(getCategoriesId(Seq(1)))
      .withPage("page123")
      .withRef("from.com")
      .withSearch("search")
      .withMobile(0)
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withSite(adSite)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.SITE_OR_APP_NOT_FOUND
  }

  it should "create bid request for correct ad request with app" in {
    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adApp = adAppBuilder
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withApp(adApp)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val app = appBuilder
      .withPublisher(publisher2)
      .withSectionCat(adApp.sectionCat.get)
      .withPageCat(adApp.pageCat.get)
      .withContent(adApp.content.get)
      .build
    val imp = ImpBuilder(adImp.id)
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withApp(app)
      .withBcat(getCategoriesId(dbPublisher2.blockedCategoriesIds))
      .withBadv(dbPublisher2.blockedDomains)
      .withUser(correctUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    factory.create(adRequest) shouldBe expectedBidRequest
  }

  it should "throw DataValidationException for ad request with inactive app" in {
    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adApp = ad.builder.AppBuilder(dbAppInactive.id)
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withApp(adApp)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.SITE_OR_APP_INACTIVE
  }

  it should "throw DataValidationException for ad request with app which not in db" in {
    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adApp = ad.builder.AppBuilder(100)
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withApp(adApp)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.SITE_OR_APP_NOT_FOUND
  }

  it should "throw DataValidationException for ad request with site and app" in {
    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adApp = adAppBuilder
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adSite = adSiteBuilder
      .withSectionCat(getCategoriesId(Seq(1, 2)))
      .withPageCat(getCategoriesId(Seq(1)))
      .withPage("page123")
      .withRef("from.com")
      .withSearch("search")
      .withMobile(0)
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withApp(adApp)
      .withSite(adSite)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
  }

  it should "create bid request for ad request with correct banner height" in {
    forAll(correctSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withHmin)
      exp.foreach(bannerBuilder.withH)
      max.foreach(bannerBuilder.withHmax)
      val banner = bannerBuilder.build

      val adImp = ad.builder.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSiteBuilder.build)
        .build

      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withBanner(banner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "create bid request for ad request with correct banner width" in {
    forAll(correctSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withWmin)
      exp.foreach(bannerBuilder.withW)
      max.foreach(bannerBuilder.withWmax)
      val banner = bannerBuilder.build

      val adImp = ad.builder.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSiteBuilder.build)
        .build

      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withBanner(banner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect banner height" in {
    forAll(incorrectSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withHmin)
      exp.foreach(bannerBuilder.withH)
      max.foreach(bannerBuilder.withHmax)
      val banner = bannerBuilder.build

      val adImp = ad.builder.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSiteBuilder.build)
        .build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "throw DataValidationException for ad request with incorrect banner width" in {
    forAll(incorrectSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withWmin)
      exp.foreach(bannerBuilder.withW)
      max.foreach(bannerBuilder.withWmax)
      val banner = bannerBuilder.build

      val adImp = ad.builder.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSiteBuilder.build)
        .build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "create bid request for ad request with correct banner" in {
    forAll(correctBanners) { (
      w: Option[Int],
      h: Option[Int],
      wmax: Option[Int],
      hmax: Option[Int],
      wmin: Option[Int],
      hmin: Option[Int],
      id: Option[String],
      btype: Option[Seq[Int]],
      battr: Option[Seq[Int]],
      pos: Option[Int],
      mimes: Option[Seq[String]],
      topFrame: Option[Int],
      expdir: Option[Seq[Int]],
      api: Option[Seq[Int]]) =>

      val banner = Banner(
        w,
        h,
        wmax,
        hmax,
        wmin,
        hmin,
        id,
        btype,
        battr,
        pos,
        mimes,
        topFrame,
        expdir,
        api,
        None)
      val adImp = ad.builder.ImpBuilder("1").withBanner(banner).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSite).build

      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withBanner(banner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect banner" in {
    forAll(incorrectBanners) { (
      w: Option[Int],
      h: Option[Int],
      wmax: Option[Int],
      hmax: Option[Int],
      wmin: Option[Int],
      hmin: Option[Int],
      id: Option[String],
      btype: Option[Seq[Int]],
      battr: Option[Seq[Int]],
      pos: Option[Int],
      mimes: Option[Seq[String]],
      topFrame: Option[Int],
      expdir: Option[Seq[Int]],
      api: Option[Seq[Int]]) =>

      val banner = Banner(
        w,
        h,
        wmax,
        hmax,
        wmin,
        hmin,
        id,
        btype,
        battr,
        pos,
        mimes,
        topFrame,
        expdir,
        api,
        None)
      val adImp = ad.builder.ImpBuilder("1").withBanner(banner).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSite).build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "create bid request for ad request with correct video" in {
    forAll(correctVideos) { (
      minDuration: Option[Int],
      maxDuration: Option[Int],
      protocol: Option[Int],
      protocols: Option[Seq[Int]],
      w: Option[Int],
      h: Option[Int],
      startDelay: Option[Int],
      linearity: Option[Int],
      sequence: Option[Int],
      battr: Option[Seq[Int]],
      maxExtended: Option[Int],
      minBitrate: Option[Int],
      maxBitrate: Option[Int],
      playbackMethod: Option[Seq[Int]],
      delivery: Option[Seq[Int]],
      pos: Option[Int],
      companionAd: Option[Seq[Banner]],
      api: Option[Seq[Int]],
      companionType: Option[Seq[Int]]) =>

      val video = Video(
        Seq("video/flv"),
        minDuration,
        maxDuration,
        protocol,
        protocols,
        w,
        h,
        startDelay,
        linearity,
        sequence,
        battr,
        maxExtended,
        minBitrate,
        maxBitrate,
        1,
        playbackMethod,
        delivery,
        pos,
        companionAd,
        api,
        companionType,
        None)
      val adImp = ad.builder.ImpBuilder("1").withVideo(video).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSite).build

      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withVideo(video).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect video" in {
    forAll(incorrectVideos) { (
      mimes: Seq[String],
      minDuration: Option[Int],
      maxDuration: Option[Int],
      protocol: Option[Int],
      protocols: Option[Seq[Int]],
      w: Option[Int],
      h: Option[Int],
      startDelay: Option[Int],
      linearity: Option[Int],
      sequence: Option[Int],
      battr: Option[Seq[Int]],
      maxExtended: Option[Int],
      minBitrate: Option[Int],
      maxBitrate: Option[Int],
      boxingAllowed: Int,
      playbackMethod: Option[Seq[Int]],
      delivery: Option[Seq[Int]],
      pos: Option[Int],
      companionAd: Option[Seq[Banner]],
      api: Option[Seq[Int]],
      companionType: Option[Seq[Int]]) =>

      val video = Video(
        mimes,
        minDuration,
        maxDuration,
        protocol,
        protocols,
        w,
        h,
        startDelay,
        linearity,
        sequence,
        battr,
        maxExtended,
        minBitrate,
        maxBitrate,
        boxingAllowed,
        playbackMethod,
        delivery,
        pos,
        companionAd,
        api,
        companionType,
        None)
      val adImp = ad.builder.ImpBuilder("1").withVideo(video).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSite).build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "create bid request for ad request with correct native" in {
    forAll(correctNatives) { (
      ver: Option[String],
      api: Option[Seq[Int]],
      battr: Option[Seq[Int]]) =>

      val native = Native("native", ver, api, battr, None)
      val adImp = ad.builder.ImpBuilder("1").withNative(native).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSite).build

      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withNative(native).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect native" in {
    forAll(incorrectNatives) { (
      request: String,
      ver: Option[String],
      api: Option[Seq[Int]],
      battr: Option[Seq[Int]]) =>

      val native = Native(request, ver, api, battr, None)
      val adImp = ad.builder.ImpBuilder("1").withNative(native).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSite).build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "throw DataValidationException for ad request without banner, video and native" in {
    val adImp = ad.builder.ImpBuilder("1").build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json).withSite(adSite).build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
  }

  it should "create bid request for ad request with correct user" in {
    forAll(correctUsers) { (
      id: Option[String],
      yob: Option[Int],
      gender: Option[String],
      keywords: Option[String],
      geo: Option[Geo]) =>

      val adUser = ad.User(id, yob, gender, keywords, geo)
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .withUser(adUser)
        .build

      val user = User(id, None, yob, gender, keywords, None, geo, None, None)
      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .withUser(user)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect user" in {
    forAll(incorrectUsers) { (
      id: Option[String],
      yob: Option[Int],
      gender: Option[String],
      keywords: Option[String],
      geo: Option[Geo]) =>

      val adUser = ad.User(id, yob, gender, keywords, geo)
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .withUser(adUser)
        .build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "create bid request for ad request with correct device" in {
    forAll(correctDevices) { device =>
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .withDevice(device)
        .build

      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .withDevice(device)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect device" in {
    forAll(incorrectDevices) { device =>
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .withDevice(device)
        .build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "create bid request for ad request with correct geo" in {
    forAll(correctGeos) { (
      lat: Option[Float],
      lon: Option[Float],
      `type`: Option[Int],
      country: Option[String],
      region: Option[String],
      regionFips104: Option[String],
      metro: Option[String],
      city: Option[String],
      zip: Option[String],
      utcOffset: Option[Int]) =>

      val geo = Geo(
        lat,
        lon,
        `type`,
        country,
        region,
        regionFips104,
        metro,
        city,
        zip,
        utcOffset,
        None)
      val adUser = ad.builder.UserBuilder().withGeo(geo).build
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .withUser(adUser)
        .build

      val user = UserBuilder().withGeo(geo).build
      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .withUser(user)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect geo" in {
    forAll(incorrectGeos) { (
      lat: Option[Float],
      lon: Option[Float],
      `type`: Option[Int],
      country: Option[String],
      region: Option[String],
      regionFips104: Option[String],
      metro: Option[String],
      city: Option[String],
      zip: Option[String],
      utcOffset: Option[Int]) =>

      val geo = Geo(
        lat,
        lon,
        `type`,
        country,
        region,
        regionFips104,
        metro,
        city,
        zip,
        utcOffset,
        None)
      val adUser = ad.builder.UserBuilder().withGeo(geo).build
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .withUser(adUser)
        .build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "create bid request for ad request with correct content" in {
    forAll(correctContents) { (
      id: Option[String],
      episode: Option[Int],
      title: Option[String],
      series: Option[String],
      season: Option[String],
      producer: Option[Producer],
      url: Option[String],
      cat: Option[Seq[String]],
      videoQuality: Option[Int],
      context: Option[Int],
      contentRating: Option[String],
      userRating: Option[String],
      qagMediaRating: Option[Int],
      keywords: Option[String],
      liveStream: Option[Int],
      sourceRelationship: Option[Int],
      len: Option[Int],
      language: Option[String],
      embeddable: Option[Int]) =>

      val content = Content(
        id,
        episode,
        title,
        series,
        season,
        producer,
        url,
        cat,
        videoQuality,
        context,
        contentRating,
        userRating,
        qagMediaRating,
        keywords,
        liveStream,
        sourceRelationship,
        len,
        language,
        embeddable,
        None)
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.withContent(content).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .build

      val site = siteBuilder
        .withPublisher(publisher1)
        .withContent(content)
        .build
      val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect content" in {
    forAll(incorrectContents) { (
      id: Option[String],
      episode: Option[Int],
      title: Option[String],
      series: Option[String],
      season: Option[String],
      producer: Option[Producer],
      url: Option[String],
      cat: Option[Seq[String]],
      videoQuality: Option[Int],
      context: Option[Int],
      contentRating: Option[String],
      userRating: Option[String],
      qagMediaRating: Option[Int],
      keywords: Option[String],
      liveStream: Option[Int],
      sourceRelationship: Option[Int],
      len: Option[Int],
      language: Option[String],
      embeddable: Option[Int]) =>

      val content = Content(
        id,
        episode,
        title,
        series,
        season,
        producer,
        url,
        cat,
        videoQuality,
        context,
        contentRating,
        userRating,
        qagMediaRating,
        keywords,
        liveStream,
        sourceRelationship,
        len,
        language,
        embeddable,
        None)
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.withContent(content).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should
    "throw DataValidationException for ad request with incorrect iab category in content" in {
    val content = ContentBuilder().withCat(Seq("notiab")).build
    val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.withContent(content).build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withSite(adSite)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.IAB_CATEGORY_NOT_FOUND
  }

  it should "create bid request for ad request with correct producer" in {
    forAll(correctProducers) { (
      id: Option[String],
      name: Option[String],
      cat: Option[Seq[String]],
      domain: Option[String]) =>

      val producer = Producer(id, name, cat, domain, None)
      val content = ContentBuilder().withProducer(producer).build
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.withContent(content).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .build

      val site = siteBuilder
        .withPublisher(publisher1)
        .withContent(content)
        .build
      val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe expectedBidRequest
    }
  }

  it should "throw DataValidationException for ad request with incorrect producer" in {
    forAll(incorrectProducers) { (
      id: Option[String],
      name: Option[String],
      cat: Option[Seq[String]],
      domain: Option[String]) =>

      val producer = Producer(id, name, cat, domain, None)
      val content = ContentBuilder().withProducer(producer).build
      val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.withContent(content).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
        .withSite(adSite)
        .build

      val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
      thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
    }
  }

  it should "throw DataValidationException for ad request with incorrect iab category in " +
    "producer" in {
    val producer = ProducerBuilder().withCat(Seq("notiab")).build
    val content = ContentBuilder().withProducer(producer).build
    val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.withContent(content).build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withSite(adSite)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.IAB_CATEGORY_NOT_FOUND
  }

  it should "create bid request for ad request with correct regs" in {
    val regs = RegsBuilder().withCoppa(1).build
    val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withSite(adSite)
      .withRegs(regs)
      .build

    val site = siteBuilder.withPublisher(publisher1).build
    val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .withRegs(regs)
      .build

    factory.create(adRequest) shouldBe expectedBidRequest
  }

  it should "throw DataValidationException for ad request with incorrect regs" in {
    val regs = RegsBuilder().withCoppa(2).build
    val adImp = ad.builder.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withSite(adSite)
      .withRegs(regs)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.INCORRECT_REQUEST
  }

  it should "not create bid request if db not contains sites" in {
    val siteDao = mock[SiteDao]
    expecting {
      siteDao.getAll.andStubReturn(Seq.empty)
      siteDao.get(anyInt).andStubReturn(None)
      siteDao.get(anyObject[Seq[Int]]).andStubReturn(Seq.empty)
      replay(siteDao)
    }
    val factory = new BidRequestFactoryImpl(categoryDao, publisherDao, siteDao, appDao)

    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adSite = adSiteBuilder
      .withSectionCat(getCategoriesId(Seq(1, 2)))
      .withPageCat(getCategoriesId(Seq(1)))
      .withPage("page123")
      .withRef("from.com")
      .withSearch("search")
      .withMobile(0)
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withSite(adSite)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.SITE_OR_APP_NOT_FOUND
  }

  it should "not create bid request if db not contains apps" in {
    val appDao = mock[AppDao]
    expecting {
      appDao.getAll.andStubReturn(Seq.empty)
      appDao.get(anyInt).andStubReturn(None)
      appDao.get(anyObject[Seq[Int]]).andStubReturn(Seq.empty)
      replay(appDao)
    }
    val factory = new BidRequestFactoryImpl(categoryDao, publisherDao, siteDao, appDao)

    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adApp = adAppBuilder
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withApp(adApp)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.SITE_OR_APP_NOT_FOUND
  }

  it should "not create bid request if db not contains categories" in {
    val categoryDao = mock[CategoryDao]
    expecting {
      categoryDao.getAll.andStubReturn(Seq.empty)
      categoryDao.get(anyInt).andStubReturn(None)
      categoryDao.get(anyObject[Seq[Int]]).andStubReturn(Seq.empty)
      replay(categoryDao)
    }
    val factory = new BidRequestFactoryImpl(categoryDao, publisherDao, siteDao, appDao)

    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adApp = adAppBuilder
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withApp(adApp)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.IAB_CATEGORY_NOT_FOUND
  }

  it should "not create bid request if db not contains publishers" in {
    val publisherDao = mock[PublisherDao]
    expecting {
      publisherDao.getAll.andStubReturn(Seq.empty)
      publisherDao.get(anyInt).andStubReturn(None)
      publisherDao.get(anyObject[Seq[Int]]).andStubReturn(Seq.empty)
      replay(publisherDao)
    }
    val factory = new BidRequestFactoryImpl(categoryDao, publisherDao, siteDao, appDao)

    val adImp = ad.builder.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adApp = adAppBuilder
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp), Json)
      .withApp(adApp)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val thrown = the[DataValidationException] thrownBy factory.create(adRequest)
    thrown.getError shouldBe ErrorCode.PUBLISHER_NOT_FOUND
  }
}
