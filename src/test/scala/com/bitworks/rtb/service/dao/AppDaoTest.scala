package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.{App, Status}
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
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
  } :: dbModule

  val expectedApp = App(
    1,
    "app",
    1,
    Status.active,
    1,
    false,
    Some("app_domain"),
    Some("keyword"),
    Seq(1),
    "bundle",
    "store",
    "ver")

  val expectedForDeleteApp = App(
    3,
    "fordelete",
    1,
    Status.active,
    1,
    false,
    Some("app_domain"),
    Some("keyword"),
    Seq(),
    "bundle",
    "store",
    "ver")

  "AppDao" should "load app by ID correctly after cache init" in {
    val appDao = inject[AppDao]

    val notFoundApp = appDao.get(expectedApp.id)
    notFoundApp should not be defined

    appDao.notify(InitCache)

    val app = appDao.get(expectedApp.id)

    app shouldBe Some(expectedApp)
  }

  it should "load some apps correctly after cache init" in {
    val appDao = inject[AppDao]
    val ids = Seq(expectedApp.id, -1, expectedForDeleteApp.id)

    val notFoundApps = appDao.get(ids)
    notFoundApps shouldBe Seq.empty

    appDao.notify(InitCache)

    val apps = appDao.get(ids)
    apps should contain theSameElementsAs Seq(expectedApp, expectedForDeleteApp)
  }

  it should "load all apps correctly after cache init" in {
    val appDao = inject[AppDao]

    val notFoundApps = appDao.getAll
    notFoundApps shouldBe Seq.empty

    appDao.notify(InitCache)

    val apps = appDao.getAll
    apps should contain theSameElementsAs Seq(expectedApp, expectedForDeleteApp)
  }

  it should "not load deleted app from DB" in {
    val appDao = inject[AppDao]

    appDao.notify(InitCache)

    val deletedApp = appDao.get(2)

    deletedApp should not be defined
  }

  it should "remove app from cache if record deleted from database" in {
    val appDao = inject[AppDao]

    appDao.notify(InitCache)
    executeQuery("app-delete.xml", Update)

    val forDeleteApp = appDao.get(expectedForDeleteApp.id)

    forDeleteApp shouldBe Some(expectedForDeleteApp)

    appDao.notify(UpdateCache)
    val deletedApp = appDao.get(expectedForDeleteApp.id)

    deletedApp should not be defined
  }

  it should "load inserted app by ID correctly after cache update" in {
    val appDao = inject[AppDao]

    appDao.notify(InitCache)
    executeQuery("app-insert.xml", Insert)

    val expectedApp = App(
      4,
      "insertedapp",
      1,
      Status.active,
      1,
      false,
      Some("app_domain"),
      Some("keyword"),
      Seq(),
      "bundle",
      "store",
      "ver")

    val notFoundApp = appDao.get(expectedApp.id)
    notFoundApp should not be defined

    appDao.notify(UpdateCache)

    val app = appDao.get(expectedApp.id)
    app shouldBe Some(expectedApp)
  }
}
