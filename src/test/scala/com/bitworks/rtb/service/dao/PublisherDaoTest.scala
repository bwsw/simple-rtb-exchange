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

  "PublisherDao" should "load publisher by ID correctly after cache init" in {
    val publisherDao = inject[PublisherDao]

    val notFoundPublisher = publisherDao.get(1)
    notFoundPublisher should not be defined

    val notFoundPublishers = publisherDao.get(Seq(1, 2, 3))
    notFoundPublishers shouldBe Seq.empty

    val noPublishers = publisherDao.getAll
    noPublishers shouldBe Seq.empty

    publisherDao.notify(InitCache)

    val expectedPublisher = Some(
      Publisher(
        1,
        "publisher",
        Seq(1),
        "domain",
        Seq("blocked_domain"),
        Seq(2)))

    val publisher = publisherDao.get(1)

    publisher shouldBe expectedPublisher
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

    val expectedForDeletePublisher = Some(
      Publisher(
        3,
        "fordelete",
        Seq(),
        "fordelete",
        Seq(),
        Seq()))

    val forDeletePublisher = publisherDao.get(3)

    forDeletePublisher shouldBe expectedForDeletePublisher

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

    val expectedPublisher = Some(
      Publisher(
        4,
        "insertedpublisher",
        Seq(),
        "inserteddomain",
        Seq(),
        Seq()))

    val publisher = publisherDao.get(4)
    publisher shouldBe expectedPublisher
  }
}
