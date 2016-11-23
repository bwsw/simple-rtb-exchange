package com.bitworks.rtb.service.factory

import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.request.{builder => ad}
import com.bitworks.rtb.model.db
import com.bitworks.rtb.model.db.IABCategory
import com.bitworks.rtb.model.request.Producer
import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.service.dao._
import org.easymock.EasyMock._
import org.easymock.IAnswer
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import scaldi.Injectable._
import scaldi.Module

/**
  * Test for [[com.bitworks.rtb.service.factory.BidRequestFactory BidRequestFactory]].
  *
  * @author Pavel Tomskikh
  */
class BidRequestFactoryTest
  extends FlatSpec
    with Matchers
    with EasyMockSugar
    with OneInstancePerTest {

  val intCapture = newCapture[Int]
  val seqIntCapture = newCapture[Seq[Int]]

  val iabCategories = Seq(
    IABCategory(1, "IAB1", "iab1", None),
    IABCategory(2, "IAB2", "iab2", None),
    IABCategory(3, "IAB3", "iab3", None),
    IABCategory(4, "IAB4", "iab4", None),
    IABCategory(5, "IAB5", "iab5", None),
    IABCategory(6, "IAB6", "iab6", None),
    IABCategory(7, "IAB7", "iab7", None))

  def getCategory(id: Int) = iabCategories.find(_.id == id)

  def getCategories(ids: Seq[Int]) = iabCategories.filter(c => ids.contains(c.id))

  def getCategoriesId(ids: Seq[Int]) = getCategories(ids).map(_.iabId)

  val correctIabsExample = getCategories(Seq(1, 2, 3)).map(_.iabId)
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

  val dbSite = db.Site(
    1, "site", 1, db.Status.active, 0, test = true, "site.com", Some("kw1,kw2"), Seq(1, 2))
  val adSiteBuilder = ad.SiteBuilder(dbSite.id)
  val siteBuilder = SiteBuilder()
    .withId(dbSite.id.toString)
    .withName(dbSite.name)
    .withDomain(dbSite.domain)
    .withKeywords(dbSite.keyword.get)
    .withCat(getCategoriesId(dbSite.iabCategoriesIds))
    .withPrivacyPolicy(dbSite.privacyPolicy)
  val dbSites = Seq(dbSite)
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

  val dbApp = db.App(
    2,
    "app",
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
  val adAppBuilder = ad.AppBuilder(dbApp.id)
  val appBuilder = AppBuilder()
    .withId(dbApp.id.toString)
    .withName(dbApp.name)
    .withCat(getCategoriesId(dbApp.iabCategoriesIds))
    .withBundle(dbApp.bundle)
    .withVer(dbApp.version)
    .withStoreUrl(dbApp.storeUrl)
    .withPrivacyPolicy(dbApp.privacyPolicy)
  val dbApps = Seq(dbApp)
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

  implicit val module = new Module {
    bind[CategoryDao] toNonLazy categoryDao
    bind[PublisherDao] toNonLazy publisherDao
    bind[SiteDao] toNonLazy siteDao
    bind[AppDao] toNonLazy appDao
    bind[BidRequestFactory] toProvider injected[BidRequestFactoryImpl]
  }

  val factory = inject[BidRequestFactory]

  val correctProducer = ProducerBuilder()
    .withId("444")
    .withName("prod")
    .withDomain("prod.com")
    .withCat(getCategoriesId(Seq(2, 3)))
    .build
  val correctContent = ContentBuilder()
    .withId("123")
    .withEpisode(1)
    .withTitle("title")
    .withSeries("series")
    .withSeason("season")
    .withProducer(correctProducer)
    .withUrl("content.com")
    .withCat(getCategoriesId(Seq(1, 3, 5)))
    .withVideoQuality(3)
    .withContext(1)
    .withContentRating("10/10")
    .withUserRating("22")
    .withQagMediaRating(2)
    .withKeywords("kw")
    .withLiveStream(0)
    .withSourceRelationship(1)
    .withLen(1000)
    .withLanguage("en")
    .withEmbeddable(1)
    .build
  val correctGeo = GeoBuilder()
    .withLat(10.5f)
    .withLon(-55.66f)
    .withType(1)
    .withCountry("GER")
    .withRegion("reg")
    .withCity("Berlin")
    .withZip("zip12412")
    .withUtcOffset(-1)
    .build
  val correctAdUser = ad.UserBuilder()
    .withId("124315")
    .withYob(1990)
    .withGender("F")
    .withKeywords("kw")
    .withGeo(correctGeo)
    .build
  val correctUser = UserBuilder()
    .withId(correctAdUser.id.get)
    .withYob(correctAdUser.yob.get)
    .withGender(correctAdUser.gender.get)
    .withKeywords(correctAdUser.keywords.get)
    .withGeo(correctAdUser.geo.get)
    .build
  val correctDevice = DeviceBuilder()
    .withUa("useragent")
    .withGeo(correctGeo)
    .withDnt(0)
    .withLmt(1)
    .withIp("8.8.8.8")
    .withIpv6("::1")
    .withDeviceType(5)
    .withMake("nokla")
    .withModel("3310")
    .withOs("android")
    .withOsv("2.4")
    .withHwv("1.1")
    .withH(200)
    .withW(100)
    .withPpi(16)
    .withPxRatio(1)
    .withJs(1)
    .withFlashVer("2")
    .withLanguage("ch")
    .withCarrier("edge")
    .withConnectionType(4)
    .withIfa("ifa")
    .withDidsha1("sha1")
    .withDidmd5("md5")
    .withDpidsha1("sha1")
    .withDpidmd5("md5")
    .withMacsha1("sha1")
    .withMacmd5("md5")
    .build
  val correctRegs = RegsBuilder().withCoppa(1).build
  val correctBanner = BannerBuilder()
    .withWmin(280)
    .withW(300)
    .withWmax(320)
    .withHmin(90)
    .withH(100)
    .withHmax(120)
    .withId("banner1")
    .withBtype(Seq(1, 4))
    .withBattr(Seq(1, 5, 16))
    .withPos(4)
    .withMimes(Seq("application/x-shockwave-flash"))
    .withTopFrame(1)
    .withExpdir(Seq(1, 2, 5))
    .withApi(Seq(2, 3))
    .build
  val correctVideo = VideoBuilder(Seq("video/x-ms-wmv", "video/x-flv"))
    .withMinDuration(10)
    .withMaxDuration(3600)
    .withProtocol(1)
    .withProtocols(Seq(1, 5, 6))
    .withW(320)
    .withH(240)
    .withStartDelay(0)
    .withLinearity(1)
    .withSequence(1)
    .withBattr(Seq(11, 16))
    .withMaxExtended(-1)
    .withMinBitrate(64)
    .withMaxBitrate(4000)
    .withBoxingAllowed(1)
    .withPlaybackMethod(Seq(2, 4))
    .withDelivery(Seq(2))
    .withPos(0)
    .withCompanionAd(Seq(correctBanner))
    .withApi(Seq(5))
    .withCompanionType(Seq(1, 2, 3))
    .build
  val correctNative = NativeBuilder("native ad")
    .withVer("1.0")
    .withApi(Seq(1, 4, 5))
    .withBattr(Seq(1, 2, 3, 7, 15, 16))
    .build

  "BidRequestFactory" should "create bid request for correct ad request with site" in {
    val adImp = ad.ImpBuilder("1")
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
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
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

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "create bid request for correct ad request with app" in {
    val adImp = ad.ImpBuilder("1")
      .withBanner(correctBanner)
      .withNative(correctNative)
      .withVideo(correctVideo)
      .build
    val adApp = adAppBuilder
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
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

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with site and app" in {
    val adImp = ad.ImpBuilder("1")
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
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withApp(adApp)
      .withSite(adSite)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    factory.create(adRequest) shouldBe None
  }

  val correctSizes = Table(
    ("min", "exp", "max"),
    (Some(1), Some(1), Some(1)),
    (Some(100), Some(200), Some(300)),
    (Some(100), Some(200), None),
    (Some(100), None, Some(300)),
    (Some(100), None, None),
    (None, Some(200), Some(300)),
    (None, Some(200), None),
    (None, None, Some(300)),
    (None, None, None))

  it should "create bid request for ad request with correct banner height" in {
    forAll(correctSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withHmin)
      exp.foreach(bannerBuilder.withH)
      max.foreach(bannerBuilder.withHmax)
      val banner = bannerBuilder.build

      val adImp = ad.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSiteBuilder.build).build

      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withBanner(banner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe Some(expectedBidRequest)
    }
  }

  it should "create bid request for ad request with correct banner width" in {
    forAll(correctSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withWmin)
      exp.foreach(bannerBuilder.withW)
      max.foreach(bannerBuilder.withWmax)
      val banner = bannerBuilder.build

      val adImp = ad.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSiteBuilder.build).build

      val site = siteBuilder.withPublisher(publisher1).build
      val imp = ImpBuilder(adImp.id).withBanner(banner).build
      val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe Some(expectedBidRequest)
    }
  }

  val incorrectSizes = Table(
    ("min", "exp", "max"),
    (Some(100), Some(99), Some(300)),
    (Some(100), Some(200), Some(199)),
    (Some(100), Some(99), Some(98)),
    (Some(100), Some(99), None),
    (Some(100), None, Some(98)),
    (Some(0), None, None),
    (None, Some(99), Some(98)),
    (None, Some(0), None),
    (None, None, Some(0)))

  it should "not create bid request for ad request with incorrect banner height" in {
    forAll(incorrectSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withHmin)
      exp.foreach(bannerBuilder.withH)
      max.foreach(bannerBuilder.withHmax)
      val banner = bannerBuilder.build

      val adImp = ad.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSiteBuilder.build).build

      factory.create(adRequest) shouldBe None
    }
  }

  it should "not create bid request for ad request with incorrect banner width" in {
    forAll(incorrectSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withWmin)
      exp.foreach(bannerBuilder.withW)
      max.foreach(bannerBuilder.withWmax)
      val banner = bannerBuilder.build

      val adImp = ad.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSiteBuilder.build).build

      factory.create(adRequest) shouldBe None
    }
  }

  it should "create bid request for ad request with correct banner" in {
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSite).build

    val site = siteBuilder.withPublisher(publisher1).build
    val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .build

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with incorrect banner" in {
    val banner = BannerBuilder().withApi(Seq(10)).build
    val adImp = ad.ImpBuilder("1").withBanner(banner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSite).build

    factory.create(adRequest) shouldBe None
  }

  it should "create bid request for ad request with correct video" in {
    val adImp = ad.ImpBuilder("1").withVideo(correctVideo).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSite).build

    val site = siteBuilder.withPublisher(publisher1).build
    val imp = ImpBuilder(adImp.id).withVideo(correctVideo).build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .build

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with incorrect video" in {
    val video = VideoBuilder(Seq("mimes")).withBattr(Seq(1, 2, 22)).build
    val adImp = ad.ImpBuilder("1").withVideo(video).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSite).build

    factory.create(adRequest) shouldBe None
  }

  it should "create bid request for ad request with correct native" in {
    val adImp = ad.ImpBuilder("1").withNative(correctNative).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSite).build

    val site = siteBuilder.withPublisher(publisher1).build
    val imp = ImpBuilder(adImp.id).withNative(correctNative).build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .build

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with incorrect native" in {
    val native = NativeBuilder("").build
    val adImp = ad.ImpBuilder("1").withNative(native).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSite).build

    factory.create(adRequest) shouldBe None
  }

  it should "not create bid request for ad request without banner, video and native" in {
    val adImp = ad.ImpBuilder("1").build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp)).withSite(adSite).build

    factory.create(adRequest) shouldBe None
  }

  it should "create bid request for ad request with correct user" in {
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .withUser(correctAdUser)
      .build

    val site = siteBuilder.withPublisher(publisher1).build
    val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .withUser(correctUser)
      .build

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with incorrect user" in {
    val user = ad.UserBuilder().withGender("unknown").build
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .withUser(user)
      .build

    factory.create(adRequest) shouldBe None
  }

  it should "create bid request for ad request with correct device" in {
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .withDevice(correctDevice)
      .build

    val site = siteBuilder.withPublisher(publisher1).build
    val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .withDevice(correctDevice)
      .build

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with incorrect device" in {
    val device = DeviceBuilder().withW(-1).build
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .withDevice(device)
      .build

    factory.create(adRequest) shouldBe None
  }

  it should "create bid request for ad request with correct geo" in {
    val adUser = ad.UserBuilder().withGeo(correctGeo).build
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .withUser(adUser)
      .build

    val user = UserBuilder().withGeo(correctGeo).build
    val site = siteBuilder.withPublisher(publisher1).build
    val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .withUser(user)
      .build

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with incorrect geo" in {
    val geo = GeoBuilder().withLat(1000).build
    val adUser = ad.UserBuilder().withGeo(geo).build
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .withUser(adUser)
      .build

    factory.create(adRequest) shouldBe None
  }

  it should "create bid request for ad request with correct content" in {
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.withContent(correctContent).build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .build

    val site = siteBuilder
      .withPublisher(publisher1)
      .withContent(correctContent)
      .build
    val imp = ImpBuilder(adImp.id).withBanner(correctBanner).build
    val expectedBidRequest = BidRequestBuilder(adRequestId, Seq(imp))
      .withSite(site)
      .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
      .withBadv(dbPublisher1.blockedDomains)
      .build

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with incorrect content" in {
    val content = ContentBuilder().withCat(Seq("notiabcat")).build
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.withContent(content).build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .build

    factory.create(adRequest) shouldBe None
  }

  val correctProducerTable = Table(
    ("id", "name", "cat", "domain"),
    (None, None, None, None),
    (Some("123"), None, None, None),
    (None, Some("prod"), None, None),
    (None, None, Some(correctIabsExample), None),
    (None, None, None, Some("prod.com")),
    (Some("123"), Some("prod"), Some(correctIabsExample), Some("prod.com"))
  )

  it should "create bid request for ad request with correct producer" in {
    forAll(correctProducerTable) { (
      id: Option[String],
      name: Option[String],
      cat: Option[Seq[String]],
      domain: Option[String]) =>

      val producer = Producer(id, name, cat, domain, None)
      val content = ContentBuilder().withProducer(producer).build
      val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.withContent(content).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
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

      factory.create(adRequest) shouldBe Some(expectedBidRequest)
    }
  }

  val incorrectProducerTable = Table(
    ("id", "name", "cat", "domain"),
    (Some(""), Some("prod"), Some(correctIabsExample), Some("prod.com")),
    (Some("123"), Some(""), Some(correctIabsExample), Some("prod.com")),
    (Some("123"), Some("prod"), Some(Seq("notiab")), Some("prod.com")),
    (Some("123"), Some("prod"), Some(correctIabsExample), Some("")),
    (Some(""), None, None, None),
    (None, Some(""), None, None),
    (None, None, Some(Seq()), None),
    (None, None, None, Some(""))
  )

  it should "not create bid request for ad request with incorrect producer" in {
    forAll(incorrectProducerTable) { (
      id: Option[String],
      name: Option[String],
      cat: Option[Seq[String]],
      domain: Option[String]) =>

      val producer = Producer(id, name, cat, domain, None)
      val content = ContentBuilder().withProducer(producer).build
      val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
      val adSite = adSiteBuilder.withContent(content).build
      val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
        .withSite(adSite)
        .build

      factory.create(adRequest) shouldBe None
    }
  }

  it should "create bid request for ad request with correct regs" in {
    val regs = RegsBuilder().withCoppa(1).build
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
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

    factory.create(adRequest) shouldBe Some(expectedBidRequest)
  }

  it should "not create bid request for ad request with incorrect regs" in {
    val regs = RegsBuilder().withCoppa(2).build
    val adImp = ad.ImpBuilder("1").withBanner(correctBanner).build
    val adSite = adSiteBuilder.build
    val adRequest = AdRequestBuilder(adRequestId, Seq(adImp))
      .withSite(adSite)
      .withRegs(regs)
      .build

    factory.create(adRequest) shouldBe None
  }
}
