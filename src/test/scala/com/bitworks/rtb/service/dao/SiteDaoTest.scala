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

  "SiteDao" should "load site by ID correctly after cache init" in {
    val siteDao = inject[SiteDao]

    val notFoundSite = siteDao.get(11)
    notFoundSite should not be defined

    siteDao.notify(InitCache)

    val expectedSite = Some(
      Site(
        11,
        "site",
        1,
        Status.active,
        1,
        false,
        "site_domain",
        Some("keyword"),
        Seq(1)))

    val site = siteDao.get(11)

    site shouldBe expectedSite
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

    val expectedForDeleteSite = Some(
      Site(
        13,
        "fordelete",
        1,
        Status.active,
        1,
        false,
        "site_domain",
        Some("keyword"),
        Seq()))

    val forDeleteSite = siteDao.get(13)

    forDeleteSite shouldBe expectedForDeleteSite

    siteDao.notify(UpdateCache)
    val deletedSite = siteDao.get(13)

    deletedSite should not be defined
  }

  it should "load inserted site by ID correctly after cache update" in {
    val siteDao = inject[SiteDao]

    siteDao.notify(InitCache)
    executeQuery("site-insert.xml", Insert)

    val notFoundSite = siteDao.get(14)
    notFoundSite should not be defined

    siteDao.notify(UpdateCache)

    val expectedSite = Some(
      Site(
        14,
        "insertedsite",
        1,
        Status.active,
        1,
        false,
        "site_domain",
        Some("keyword"),
        Seq()))

    val site = siteDao.get(14)
    site shouldBe expectedSite
  }
}
