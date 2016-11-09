package com.bitworks.rtb.service.dao

import com.bitworks.rtb.application.RtbModule
import com.bitworks.rtb.model.db.DisplayManager
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import org.scalatest.OptionValues._
import scaldi.Injectable._
import scaldi.Module

/**
  * Test for [[com.bitworks.rtb.service.dao.DisplayManagerDaoImpl DisplayManagerDaoImpl]]
  *
  * @author Egor Ilchenko
  */
class DisplayManagerDaoTest extends BaseDaoTest {
  implicit val displayManagerModule = new Module {
    bind[DisplayManagerDao] toProvider injected[DisplayManagerDaoImpl] // new instance per inject
  } :: new RtbModule

  "DisplayManagerDao" should "load display manager by ID correctly after cache init" in {
    val displayManagerDao = inject[DisplayManagerDao]

    val notFoundDisplayManager = displayManagerDao.get(1)
    notFoundDisplayManager should not be defined

    displayManagerDao.notify(InitCache)

    val displayManager = displayManagerDao.get(1)

    displayManager shouldBe defined
    displayManager.value.name shouldBe "manager"
    displayManager.value.ver shouldBe "ver"
  }

  it should "not load deleted display manager from DB" in {
    val displayManagerDao = inject[DisplayManagerDao]

    displayManagerDao.notify(InitCache)

    val deletedDisplayManager = displayManagerDao.get(2)

    deletedDisplayManager should not be defined
  }

  it should "remove display manager from cache if record deleted from database" in {
    val displayManagerDao = inject[DisplayManagerDao]

    displayManagerDao.notify(InitCache)
    executeQuery("displaymanager-delete.xml", Update)

    val forDeleteDisplayManager = displayManagerDao.get(3)

    forDeleteDisplayManager shouldBe defined
    forDeleteDisplayManager.value.name shouldBe "fordelete"
    forDeleteDisplayManager.value.ver shouldBe "fordelete"

    displayManagerDao.notify(UpdateCache)
    val deletedDisplayManager = displayManagerDao.get(3)

    deletedDisplayManager should not be defined
  }

  it should "load inserted display manager by ID correctly after cache update" in {
    val displayManagerDao = inject[DisplayManagerDao]

    displayManagerDao.notify(InitCache)
    executeQuery("displaymanager-insert.xml", Insert)

    val notFoundDisplayManager = displayManagerDao.get(4)
    notFoundDisplayManager should not be defined

    displayManagerDao.notify(UpdateCache)

    val displayManager = displayManagerDao.get(4)
    displayManager shouldBe defined
    displayManager.value.name shouldBe "insertedname"
    displayManager.value.ver shouldBe "insertedver"
  }

  it should "load display manager by owner ID correctly" in {
    val displayManagerDao = inject[DisplayManagerDao]

    val emptyList = displayManagerDao.getByOwnerId(11)
    emptyList shouldBe empty

    displayManagerDao.notify(InitCache)

    val displayManagers = displayManagerDao.getByOwnerId(11)

    displayManagers should not be empty
    displayManagers should contain theSameElementsAs Seq(DisplayManager(1, "manager", "ver"))
  }
}
