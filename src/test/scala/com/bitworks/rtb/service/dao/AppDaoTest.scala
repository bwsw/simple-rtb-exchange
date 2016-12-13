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

  "AppDao" should "load app by ID correctly after cache init" in {
    val appDao = inject[AppDao]

    appDao.notify(InitCache)

    val expectedApp = Some(
      App(
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
        "ver"))

    val app = appDao.get(1)

    app shouldBe expectedApp
  }

  it should "not load app before cache init" in {
    val appDao = inject[AppDao]

    val notFoundApp = appDao.get(1)
    notFoundApp should not be defined
  }

  it should "not load some apps before cache init" in {
    val appDao = inject[AppDao]

    val notFoundApp = appDao.get(Seq(1, 2, 3))
    notFoundApp shouldBe Seq.empty
  }

  it should "not load any apps before cache init" in {
    val appDao = inject[AppDao]

    val notFoundApp = appDao.getAll
    notFoundApp shouldBe Seq.empty
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

    val expectedForDeleteApp = Some(
      App(
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
        "ver"))

    val forDeleteApp = appDao.get(3)

    forDeleteApp shouldBe expectedForDeleteApp

    appDao.notify(UpdateCache)
    val deletedApp = appDao.get(3)

    deletedApp should not be defined
  }

  it should "load inserted app by ID correctly after cache update" in {
    val appDao = inject[AppDao]

    appDao.notify(InitCache)
    executeQuery("app-insert.xml", Insert)

    val notFoundApp = appDao.get(4)
    notFoundApp should not be defined

    appDao.notify(UpdateCache)

    val expectedApp = Some(
      App(
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
        "ver"))
    val app = appDao.get(4)
    app shouldBe expectedApp
  }
}
