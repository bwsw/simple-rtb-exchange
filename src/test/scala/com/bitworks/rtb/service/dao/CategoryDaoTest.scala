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

  val parentCategory = IABCategory(
    1,
    "IAB1",
    "parent",
    None)

  val expectedCategory = IABCategory(
    2,
    "IAB2",
    "iabname",
    Some(1))

  val expectedForDeleteCategory = IABCategory(
    4,
    "fordelet",
    "fordelete",
    None)

  "CategoryDao" should "load category by ID correctly after cache init" in {
    val categoryDao = inject[CategoryDao]

    val notFoundCategory = categoryDao.get(expectedCategory.id)
    notFoundCategory should not be defined

    categoryDao.notify(InitCache)

    val category = categoryDao.get(expectedCategory.id)

    category shouldBe Some(expectedCategory)
  }

  it should "load some categories correctly after cache init" in {
    val categoryDao = inject[CategoryDao]
    val ids = Seq(expectedCategory.id, -1, expectedForDeleteCategory.id)

    val notFoundCategories = categoryDao.get(ids)
    notFoundCategories shouldBe Seq.empty

    categoryDao.notify(InitCache)

    val categories = categoryDao.get(ids)
    categories should contain theSameElementsAs Seq(expectedCategory, expectedForDeleteCategory)
  }

  it should "load all categories correctly after cache init" in {
    val categoryDao = inject[CategoryDao]

    val notFoundCategories = categoryDao.getAll
    notFoundCategories shouldBe Seq.empty

    categoryDao.notify(InitCache)

    val categories = categoryDao.getAll
    categories should contain theSameElementsAs Seq(
      parentCategory,
      expectedCategory,
      expectedForDeleteCategory)
  }

  it should "not load any categories before cache init" in {
    val categoryDao = inject[CategoryDao]

    val notFoundCategory = categoryDao.getAll
    notFoundCategory shouldBe Seq.empty
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

    val forDeleteCategory = categoryDao.get(expectedForDeleteCategory.id)

    forDeleteCategory shouldBe Some(expectedForDeleteCategory)

    categoryDao.notify(UpdateCache)
    val deletedCategory = categoryDao.get(expectedForDeleteCategory.id)

    deletedCategory should not be defined
  }

  it should "load inserted category by ID correctly after cache update" in {
    val categoryDao = inject[CategoryDao]

    categoryDao.notify(InitCache)
    executeQuery("category-insert.xml", Insert)

    val expectedCategory = IABCategory(
      5,
      "IAB3",
      "inserted",
      None)

    val notFoundCategory = categoryDao.get(expectedCategory.id)
    notFoundCategory should not be defined

    categoryDao.notify(UpdateCache)

    val category = categoryDao.get(expectedCategory.id)
    category shouldBe Some(expectedCategory)
  }
}
