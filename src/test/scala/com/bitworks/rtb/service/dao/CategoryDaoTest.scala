package com.bitworks.rtb.service.dao

import com.bitworks.rtb.application.RtbModule
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import org.scalatest.OptionValues._
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
  } :: new RtbModule

  "CategoryDao" should "load category by ID correctly after cache init" in {
    val categoryDao = inject[CategoryDao]

    val notFoundCategory = categoryDao.get(2)
    notFoundCategory should not be defined

    categoryDao.notify(InitCache)

    val category = categoryDao.get(2)

    category shouldBe defined
    category.value.iabId shouldBe "IAB2"
    category.value.name shouldBe "iabname"
    category.value.parentId shouldBe defined
    category.value.parentId.value shouldBe 1
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

    val forDeleteCategory = categoryDao.get(4)

    forDeleteCategory shouldBe defined
    forDeleteCategory.value.iabId shouldBe "fordelet"
    forDeleteCategory.value.name shouldBe "fordelete"
    forDeleteCategory.value.parentId should not be defined

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

    val category = categoryDao.get(5)
    category shouldBe defined
    category.value.iabId shouldBe "IAB3"
    category.value.name shouldBe "inserted"
    category.value.parentId should not be defined
  }
}
