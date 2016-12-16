package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.{Site, Status}
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import scaldi.Injectable._
import scaldi.Module

/**
  * Test for [[com.bitworks.rtb.service.dao.SiteDaoImpl SiteDaoImpl]].
  *
  * @author Egor Ilchenko
  */
class SiteDaoTest extends BaseDaoTest {

  implicit val siteModule = new Module {
    bind[SiteDao] toProvider injected[SiteDaoImpl] // new instance per inject
  } :: dbModule

  val expectedSite = Site(
    11,
    "site",
    1,
    Status.active,
    1,
    false,
    "site_domain",
    Some("keyword"),
    Seq(1))

  val expectedForDeleteSite = Site(
    13,
    "fordelete",
    1,
    Status.active,
    1,
    false,
    "site_domain",
    Some("keyword"),
    Seq())

  "SiteDao" should "load site by ID correctly after cache init" in {
    val siteDao = inject[SiteDao]

    val notFoundSite = siteDao.get(expectedSite.id)
    notFoundSite should not be defined

    siteDao.notify(InitCache)

    val site = siteDao.get(expectedSite.id)

    site shouldBe Some(expectedSite)
  }

  it should "load some sites correctly after cache init" in {
    val siteDao = inject[SiteDao]
    val ids = Seq(expectedSite.id, -1, expectedForDeleteSite.id)

    val notFoundSites = siteDao.get(ids)
    notFoundSites shouldBe Seq.empty

    siteDao.notify(InitCache)

    val sites = siteDao.get(ids)
    sites should contain theSameElementsAs Seq(expectedSite, expectedForDeleteSite)
  }

  it should "load all sites correctly after cache init" in {
    val siteDao = inject[SiteDao]

    val notFoundSites = siteDao.getAll
    notFoundSites shouldBe Seq.empty

    siteDao.notify(InitCache)

    val sites = siteDao.getAll
    sites should contain theSameElementsAs Seq(expectedSite, expectedForDeleteSite)
  }

  it should "not load deleted site from DB" in {
    val siteDao = inject[SiteDao]

    siteDao.notify(InitCache)

    val deletedSite = siteDao.get(12)

    deletedSite should not be defined
  }

  it should "remove site from cache if record deleted from database" in {
    val siteDao = inject[SiteDao]

    siteDao.notify(InitCache)
    executeQuery("site-delete.xml", Update)

    val forDeleteSite = siteDao.get(expectedForDeleteSite.id)

    forDeleteSite shouldBe Some(expectedForDeleteSite)

    siteDao.notify(UpdateCache)
    val deletedSite = siteDao.get(expectedForDeleteSite.id)

    deletedSite should not be defined
  }

  it should "load inserted site by ID correctly after cache update" in {
    val siteDao = inject[SiteDao]

    siteDao.notify(InitCache)
    executeQuery("site-insert.xml", Insert)

    val expectedSite = Site(
      14,
      "insertedsite",
      1,
      Status.active,
      1,
      false,
      "site_domain",
      Some("keyword"),
      Seq())

    val notFoundSite = siteDao.get(expectedSite.id)
    notFoundSite should not be defined

    siteDao.notify(UpdateCache)

    val site = siteDao.get(expectedSite.id)
    site shouldBe Some(expectedSite)
  }
}
