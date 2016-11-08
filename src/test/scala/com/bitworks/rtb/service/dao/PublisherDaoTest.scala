package com.bitworks.rtb.service.dao

import com.bitworks.rtb.application.RtbModule
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import org.scalatest.OptionValues._
import scaldi.Injectable._
import scaldi.Module

/**
  * Test for [[com.bitworks.rtb.service.dao.PublisherDaoImpl PublisherDaoImpl]].
  *
  * @author Egor Ilchenko
  */
class PublisherDaoTest extends BaseDaoTest {

  implicit val PublisherModule = new Module {
    bind[PublisherDao] toProvider injected[PublisherDaoImpl] // new instance per inject
  } :: new RtbModule

  "PublisherDao" should "load publisher by ID correctly after cache init" in {
    val publisherDao = inject[PublisherDao]
    val categoriesDao = inject[CategoryDao]
    categoriesDao.notify(InitCache)

    val notFoundPublisher = publisherDao.get(1)
    notFoundPublisher should not be defined

    publisherDao.notify(InitCache)

    val publisher = publisherDao.get(1)

    publisher shouldBe defined
    val p = publisher.value

    p.name shouldBe "publisher"
    p.domain shouldBe "domain"

    p.categories.map(_.iabId) should contain theSameElementsAs Seq("IAB1")

    p.blockedCategories.map(_.iabId) should contain theSameElementsAs Seq("IAB2")

    p.blockedDomains should contain theSameElementsAs Seq("blocked_domain")
  }

  it should "not load deleted publisher from DB" in {
    val publisherDao = inject[PublisherDao]

    publisherDao.notify(InitCache)

    val deletedPublisher = publisherDao.get(2)

    deletedPublisher should not be defined
  }

  it should "remove publisher from cache if record deleted from database" in {
    val publisherDao = inject[PublisherDao]

    publisherDao.notify(InitCache)
    executeQuery("publisher-delete.xml", Update)

    val forDeletePublisher = publisherDao.get(3)

    forDeletePublisher shouldBe defined
    forDeletePublisher.value.name shouldBe "fordelete"
    forDeletePublisher.value.domain shouldBe "fordelete"

    publisherDao.notify(UpdateCache)
    val deletedPublisher = publisherDao.get(3)

    deletedPublisher should not be defined
  }

  it should "load inserted publisher by ID correctly after cache update" in {
    val publisherDao = inject[PublisherDao]

    publisherDao.notify(InitCache)
    executeQuery("publisher-insert.xml", Insert)

    val notFoundPublisher = publisherDao.get(4)
    notFoundPublisher should not be defined

    publisherDao.notify(UpdateCache)

    val publisher = publisherDao.get(4)
    publisher shouldBe defined
    publisher.value.name shouldBe "insertedpublisher"
    publisher.value.domain shouldBe "inserteddomain"
  }
}
