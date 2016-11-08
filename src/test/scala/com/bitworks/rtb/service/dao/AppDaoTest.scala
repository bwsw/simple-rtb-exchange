package com.bitworks.rtb.service.dao

import com.bitworks.rtb.application.RtbModule
import com.bitworks.rtb.model.db.Status
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import org.scalatest.OptionValues._
import scaldi.Injectable._
import scaldi.Module

/**
  * Test for [[com.bitworks.rtb.service.dao.AppDaoImpl AppDaoImpl]].
  *
  * @author Egor Ilchenko
  */
class AppDaoTest extends BaseDaoTest {

  implicit val appModule = new Module {
    bind[AppDao] toProvider injected[AppDaoImpl] // new instance per inject
  } :: new RtbModule

  "AppDao" should "load app by ID correctly after cache init" in {
    val appDao = inject[AppDao]
    val categoryDao = inject[CategoryDao]
    val publisherDao = inject[PublisherDao]
    categoryDao.notify(InitCache)
    publisherDao.notify(InitCache)

    val notFoundApp = appDao.get(1)
    notFoundApp should not be defined

    appDao.notify(InitCache)

    val app = appDao.get(1)

    app shouldBe defined
    val a = app.value
    a.publisher.id shouldBe 1
    a.publisher.name shouldBe "publisher"
    a.name shouldBe "app"
    a.status shouldBe Status.active
    a.privacyPolicy shouldBe 1
    a.test shouldBe false
    a.domain shouldBe Some("app_domain")
    a.keyword shouldBe Some("keyword")
    a.bundle shouldBe "bundle"
    a.storeUrl shouldBe "store"
    a.version shouldBe "ver"
    a.iabCategories.map(_.iabId) should contain theSameElementsAs Seq("IAB1")
  }

  it should "not load deleted app from DB" in {
    val appDao = inject[AppDao]

    appDao.notify(InitCache)

    val deletedApp = appDao.get(2)

    deletedApp should not be defined
  }

  it should "remove app from cache if record deleted from database" in {
    val appDao = inject[AppDao]
    val categoryDao = inject[CategoryDao]
    val publisherDao = inject[PublisherDao]
    categoryDao.notify(InitCache)
    publisherDao.notify(InitCache)

    appDao.notify(InitCache)
    executeQuery("app-delete.xml", Update)

    val forDeleteApp = appDao.get(3)

    forDeleteApp shouldBe defined
    forDeleteApp.value.name shouldBe "fordelete"

    appDao.notify(UpdateCache)
    val deletedApp = appDao.get(3)

    deletedApp should not be defined
  }

  it should "load inserted app by ID correctly after cache update" in {
    val appDao = inject[AppDao]
    val categoryDao = inject[CategoryDao]
    val publisherDao = inject[PublisherDao]
    categoryDao.notify(InitCache)
    publisherDao.notify(InitCache)

    appDao.notify(InitCache)
    executeQuery("app-insert.xml", Insert)

    val notFoundApp = appDao.get(4)
    notFoundApp should not be defined

    appDao.notify(UpdateCache)

    val app = appDao.get(4)
    app shouldBe defined
    app.value.name shouldBe "insertedapp"
  }
}
