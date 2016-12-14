package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.Publisher
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
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
  } :: dbModule

  val expectedPublisher = Publisher(
    1,
    "publisher",
    Seq(1),
    "domain",
    Seq("blocked_domain"),
    Seq(2))

  val expectedForDeletePublisher = Publisher(
    3,
    "fordelete",
    Seq(),
    "fordelete",
    Seq(),
    Seq())

  "PublisherDao" should "load publisher by ID correctly after cache init" in {
    val publisherDao = inject[PublisherDao]

    val notFoundPublisher = publisherDao.get(expectedPublisher.id)
    notFoundPublisher should not be defined

    publisherDao.notify(InitCache)

    val publisher = publisherDao.get(expectedPublisher.id)

    publisher shouldBe Some(expectedPublisher)
  }

  it should "load some publishers correctly after cache init" in {
    val publisherDao = inject[PublisherDao]
    val ids = Seq(expectedPublisher.id, -1, expectedForDeletePublisher.id)

    val notFoundPublishers = publisherDao.get(ids)
    notFoundPublishers shouldBe Seq.empty

    publisherDao.notify(InitCache)

    val publishers = publisherDao.get(ids)
    publishers should contain theSameElementsAs Seq(expectedPublisher, expectedForDeletePublisher)
  }

  it should "load all publishers correctly after cache init" in {
    val publisherDao = inject[PublisherDao]

    val notFoundPublishers = publisherDao.getAll
    notFoundPublishers shouldBe Seq.empty

    publisherDao.notify(InitCache)

    val publishers = publisherDao.getAll
    publishers should contain theSameElementsAs Seq(expectedPublisher, expectedForDeletePublisher)
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

    val forDeletePublisher = publisherDao.get(expectedForDeletePublisher.id)

    forDeletePublisher shouldBe Some(expectedForDeletePublisher)

    publisherDao.notify(UpdateCache)
    val deletedPublisher = publisherDao.get(expectedForDeletePublisher.id)

    deletedPublisher should not be defined
  }

  it should "load inserted publisher by ID correctly after cache update" in {
    val publisherDao = inject[PublisherDao]

    publisherDao.notify(InitCache)
    executeQuery("publisher-insert.xml", Insert)

    val expectedPublisher = Publisher(
      4,
      "insertedpublisher",
      Seq(),
      "inserteddomain",
      Seq(),
      Seq())

    val notFoundPublisher = publisherDao.get(expectedPublisher.id)
    notFoundPublisher should not be defined

    publisherDao.notify(UpdateCache)

    val publisher = publisherDao.get(expectedPublisher.id)
    publisher shouldBe Some(expectedPublisher)
  }
}
