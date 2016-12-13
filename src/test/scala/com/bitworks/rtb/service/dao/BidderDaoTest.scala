package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import scaldi.Injectable._
import scaldi.Module

/**
  * Test for [[com.bitworks.rtb.service.dao.BidderDaoImpl BidderDaoImpl]].
  *
  * @author Egor Ilchenko
  */
class BidderDaoTest extends BaseDaoTest {

  implicit val bidderModule = new Module {
    bind[BidderDao] toProvider injected[BidderDaoImpl] // new instance per inject
  } :: dbModule

  val expectedBidder = Bidder(
    1,
    "bidder",
    "endpoint")

  val expectedForDeleteBidder = Bidder(
    3,
    "fordelete",
    "fordelete")

  "BidderDao" should "load bidder by ID correctly after cache init" in {
    val bidderDao = inject[BidderDao]

    val notFoundBidder = bidderDao.get(expectedBidder.id)
    notFoundBidder should not be defined

    bidderDao.notify(InitCache)

    val bidder = bidderDao.get(expectedBidder.id)

    bidder shouldBe Some(expectedBidder)
  }

  it should "load some bidders correctly after cache init" in {
    val bidderDao = inject[BidderDao]
    val ids = Seq(expectedBidder.id, -1, expectedForDeleteBidder.id)

    val notFoundBidders = bidderDao.get(ids)
    notFoundBidders shouldBe Seq.empty

    bidderDao.notify(InitCache)

    val bidders = bidderDao.get(ids)
    bidders should contain theSameElementsAs Seq(expectedBidder, expectedForDeleteBidder)
  }

  it should "load all bidders correctly after cache init" in {
    val bidderDao = inject[BidderDao]

    val notFoundBidders = bidderDao.getAll
    notFoundBidders shouldBe Seq.empty

    bidderDao.notify(InitCache)

    val bidders = bidderDao.getAll
    bidders should contain theSameElementsAs Seq(expectedBidder, expectedForDeleteBidder)
  }

  it should "not load deleted bidder from DB" in {
    val bidderDao = inject[BidderDao]

    bidderDao.notify(InitCache)

    val deletedBidder = bidderDao.get(2)

    deletedBidder should not be defined
  }

  it should "remove bidder from cache if record deleted from database" in {
    val bidderDao = inject[BidderDao]

    bidderDao.notify(InitCache)
    executeQuery("bidder-delete.xml", Update)

    val forDeleteBidder = bidderDao.get(expectedForDeleteBidder.id)

    forDeleteBidder shouldBe Some(expectedForDeleteBidder)

    bidderDao.notify(UpdateCache)
    val deletedBidder = bidderDao.get(expectedForDeleteBidder.id)

    deletedBidder should not be defined
  }

  it should "load inserted bidder by ID correctly after cache update" in {
    val bidderDao = inject[BidderDao]

    bidderDao.notify(InitCache)
    executeQuery("bidder-insert.xml", Insert)

    val expectedBidder = Bidder(
      4,
      "insertedbidder",
      "insertedendpoint")

    val notFoundBidder = bidderDao.get(expectedBidder.id)
    notFoundBidder should not be defined

    bidderDao.notify(UpdateCache)

    val bidder = bidderDao.get(expectedBidder.id)
    bidder shouldBe Some(expectedBidder)
  }
}
