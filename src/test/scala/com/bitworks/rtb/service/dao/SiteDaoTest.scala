package com.bitworks.rtb.service.dao

import com.bitworks.rtb.application.RtbModule
import com.bitworks.rtb.model.db.Status
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import org.scalatest.OptionValues._
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
  } :: new RtbModule

  "SiteDao" should "load site by ID correctly after cache init" in {
    val siteDao = inject[SiteDao]

    val notFoundSite = siteDao.get(11)
    notFoundSite should not be defined

    siteDao.notify(InitCache)

    val site = siteDao.get(11)

    site shouldBe defined
    val a = site.value
    a.publisherId shouldBe 1
    a.name shouldBe "site"
    a.status shouldBe Status.active
    a.privacyPolicy shouldBe 1
    a.test shouldBe false
    a.domain shouldBe "site_domain"
    a.keyword shouldBe Some("keyword")
    a.iabCategoriesIds should contain theSameElementsAs Seq(1)
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

    val forDeleteSite = siteDao.get(13)

    forDeleteSite shouldBe defined
    forDeleteSite.value.name shouldBe "fordelete"

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

    val site = siteDao.get(14)
    site shouldBe defined
    site.value.name shouldBe "insertedsite"
  }
}
