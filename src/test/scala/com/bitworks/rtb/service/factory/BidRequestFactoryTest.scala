package com.bitworks.rtb.service.factory

import com.bitworks.rtb.application.RtbModule
import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.request.{builder => ad}
import com.bitworks.rtb.model.db
import com.bitworks.rtb.model.db.IABCategory
import com.bitworks.rtb.model.request.builder._
import com.bitworks.rtb.service.dao._
import org.scalamock.scalatest.MockFactory
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import scaldi.Injectable._

/**
  * Test for [[com.bitworks.rtb.service.factory.BidRequestFactory BidRequestFactory]].
  *
  * @author Pavel Tomskikh
  */
class BidRequestFactoryTest
  extends FlatSpec
    with Matchers
    with MockFactory
    with OneInstancePerTest {

  implicit val predefined = new RtbModule

  class CategoryDaoMock extends CategoryDaoImpl(inject[DbContext], inject[CacheUpdater])

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

  val categoryDao = stub[CategoryDaoMock]
  (categoryDao.getAll _).when().returns(iabCategories)
  (categoryDao.get(_: Int)).when(*).onCall(getCategory _)
  (categoryDao.get(_: Seq[Int])).when(*).onCall(getCategories _)

  class PublisherDaoMock extends PublisherDaoImpl(inject[DbContext], inject[CacheUpdater])

  val dbPublisher1 = db.Publisher(
    1, "pub1", Seq(1, 2, 3), "pub1.com", Seq("block1.com", "block2.com"), Seq(6, 7))
  val publisher1Builder = PublisherBuilder()
    .withId(dbPublisher1.id.toString)
    .withName(dbPublisher1.name)
    .withCat(getCategoriesId(dbPublisher1.categoriesIds))
    .withDomain(dbPublisher1.domain)
  val dbPublisher2 = db.Publisher(
    2, "pub2", Seq(3, 4, 5), "pub2.com", Seq("block2.com", "block3.com"), Seq(1, 2))
  val publisher2Builder = PublisherBuilder()
    .withId(dbPublisher2.id.toString)
    .withName(dbPublisher2.name)
    .withCat(getCategoriesId(dbPublisher2.categoriesIds))
    .withDomain(dbPublisher2.domain)
  val dbPublishers = Seq(dbPublisher1, dbPublisher2)
  val publisherDao = stub[PublisherDaoMock]
  (publisherDao.get(_: Int)).when(*).onCall { i: Int => dbPublishers.find(_.id == i) }

  class SiteDaoMock extends SiteDaoImpl(inject[DbContext], inject[CacheUpdater])

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
  val siteDao = stub[SiteDaoMock]
  (siteDao.get(_: Int)).when(*).onCall { i: Int => dbSites.find(_.id == i) }

  class AppDaoMock extends AppDaoImpl(inject[DbContext], inject[CacheUpdater])

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
  val appDao = stub[AppDaoMock]
  (appDao.get(_: Int)).when(*).onCall { i: Int => dbApps.find(_.id == i) }

  val bidRequestId = "12345"

  class BidRequestFactoryMock
    extends BidRequestFactoryImpl(categoryDao, publisherDao, siteDao, appDao) {
    override def genId() = bidRequestId
  }

  val factory = new BidRequestFactoryMock

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
    .withMake("make")
    .withModel("model")
    .build
  val correctRegs = RegsBuilder().withCoppa(1).build

  "BidRequestFactory" should "create bid request for correct ad request with site" in {
    val banner = BannerBuilder().build
    val adImp = ad.ImpBuilder("1").withBanner(banner).build
    val adSite = adSiteBuilder
      .withSectionCat(getCategoriesId(Seq(1, 2)))
      .withPageCat(getCategoriesId(Seq(1)))
      .withPage("page123")
      .withRef("from.com")
      .withSearch("search")
      .withMobile(0)
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(Seq(adImp))
      .withSite(adSite)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val site = siteBuilder.withPublisher(publisher1Builder.build)
      .withSectionCat(adSite.sectionCat.get)
      .withPageCat(adSite.pageCat.get)
      .withPage(adSite.page.get)
      .withRef(adSite.ref.get)
      .withSearch(adSite.search.get)
      .withMobile(adSite.mobile.get)
      .withContent(adSite.content.get)
      .build
    val imp = ImpBuilder(adImp.id).withBanner(banner).build
    val expectedBidRequest = BidRequestBuilder(bidRequestId, Seq(imp))
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
    val banner = BannerBuilder().build
    val adImp = ad.ImpBuilder("1").withBanner(banner).build
    val adApp = adAppBuilder
      .withSectionCat(getCategoriesId(Seq(5, 6)))
      .withPageCat(getCategoriesId(Seq(5)))
      .withContent(correctContent)
      .build
    val adRequest = AdRequestBuilder(Seq(adImp))
      .withApp(adApp)
      .withUser(correctAdUser)
      .withDevice(correctDevice)
      .withRegs(correctRegs)
      .withTmax(500)
      .build

    val app = appBuilder
      .withPublisher(publisher2Builder.build)
      .withSectionCat(adApp.sectionCat.get)
      .withPageCat(adApp.pageCat.get)
      .withContent(adApp.content.get)
      .build
    val imp = ImpBuilder(adImp.id).withBanner(banner).build
    val expectedBidRequest = BidRequestBuilder(bidRequestId, Seq(imp))
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
    val banner = BannerBuilder().build
    val adImp = ad.ImpBuilder("1").withBanner(banner).build
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
    val adRequest = AdRequestBuilder(Seq(adImp))
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

  it should "create bid request for ad request with correct banner heights" in {
    forAll(correctSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withHmin)
      exp.foreach(bannerBuilder.withH)
      max.foreach(bannerBuilder.withHmax)
      val banner = bannerBuilder.build

      val adImp = ad.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(Seq(adImp)).withSite(adSiteBuilder.build).build

      val site = siteBuilder.withPublisher(publisher1Builder.build).build
      val imp = ImpBuilder(adImp.id).withBanner(banner).build
      val expectedBidRequest = BidRequestBuilder(bidRequestId, Seq(imp))
        .withSite(site)
        .withBcat(getCategoriesId(dbPublisher1.blockedCategoriesIds))
        .withBadv(dbPublisher1.blockedDomains)
        .build

      factory.create(adRequest) shouldBe Some(expectedBidRequest)
    }
  }

  it should "create bid request for ad request with correct banner widths" in {
    forAll(correctSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withWmin)
      exp.foreach(bannerBuilder.withW)
      max.foreach(bannerBuilder.withWmax)
      val banner = bannerBuilder.build

      val adImp = ad.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(Seq(adImp)).withSite(adSiteBuilder.build).build

      val site = siteBuilder.withPublisher(publisher1Builder.build).build
      val imp = ImpBuilder(adImp.id).withBanner(banner).build
      val expectedBidRequest = BidRequestBuilder(bidRequestId, Seq(imp))
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

  it should "not create bid request for ad request with incorrect banner heights" in {
    forAll(incorrectSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withHmin)
      exp.foreach(bannerBuilder.withH)
      max.foreach(bannerBuilder.withHmax)
      val banner = bannerBuilder.build

      val adImp = ad.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(Seq(adImp)).withSite(adSiteBuilder.build).build

      factory.create(adRequest) shouldBe None
    }
  }

  it should "not create bid request for ad request with incorrect banner widths" in {
    forAll(incorrectSizes) { (min: Option[Int], exp: Option[Int], max: Option[Int]) =>
      val bannerBuilder = BannerBuilder()
      min.foreach(bannerBuilder.withWmin)
      exp.foreach(bannerBuilder.withW)
      max.foreach(bannerBuilder.withWmax)
      val banner = bannerBuilder.build

      val adImp = ad.ImpBuilder("1").withBanner(banner).build
      val adRequest = AdRequestBuilder(Seq(adImp)).withSite(adSiteBuilder.build).build

      factory.create(adRequest) shouldBe None
    }
  }

}
