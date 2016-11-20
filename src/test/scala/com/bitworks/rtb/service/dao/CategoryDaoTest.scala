package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.IABCategory
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import scaldi.Injectable._
import scaldi.Module

/**
  * Test for [[com.bitworks.rtb.service.dao.CategoryDaoImpl CategoryDaoImpl]]
  *
  * @author Egor Ilchenko
  */
class CategoryDaoTest extends BaseDaoTest {
  implicit val categoryModule = new Module {
    bind[CategoryDao] toProvider injected[CategoryDaoImpl] // new instance per inject
  } :: dbModule

  "CategoryDao" should "load category by ID correctly after cache init" in {
    val categoryDao = inject[CategoryDao]

    val notFoundCategory = categoryDao.get(2)
    notFoundCategory should not be defined

    categoryDao.notify(InitCache)

    val expectedCategory = Some(
      IABCategory(
        2,
        "IAB2",
        "iabname",
        Some(1)))

    val category = categoryDao.get(2)

    category shouldBe expectedCategory
  }

  it should "not load deleted category from DB" in {
    val categoryDao = inject[CategoryDao]

    categoryDao.notify(InitCache)

    val deletedCategory = categoryDao.get(3)

    deletedCategory should not be defined
  }

  it should "remove category from cache if record deleted from database" in {
    val categoryDao = inject[CategoryDao]

    categoryDao.notify(InitCache)
    executeQuery("category-delete.xml", Update)

    val expectedForDeleteCategory = Some(
      IABCategory(
        4,
        "fordelet",
        "fordelete",
        None))

    val forDeleteCategory = categoryDao.get(4)

    forDeleteCategory shouldBe expectedForDeleteCategory

    categoryDao.notify(UpdateCache)
    val deletedCategory = categoryDao.get(4)

    deletedCategory should not be defined
  }

  it should "load inserted category by ID correctly after cache update" in {
    val categoryDao = inject[CategoryDao]

    categoryDao.notify(InitCache)
    executeQuery("category-insert.xml", Insert)

    val notFoundCategory = categoryDao.get(5)
    notFoundCategory should not be defined

    categoryDao.notify(UpdateCache)

    val expectedCategory = Some(
      IABCategory(
        5,
        "IAB3",
        "inserted",
        None))

    val category = categoryDao.get(5)

    category shouldBe expectedCategory
  }
}
