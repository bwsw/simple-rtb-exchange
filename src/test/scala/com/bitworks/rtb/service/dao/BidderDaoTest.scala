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

  "BidderDao" should "load bidder by ID correctly after cache init" in {
    val bidderDao = inject[BidderDao]

    val notFoundBidder = bidderDao.get(1)
    notFoundBidder should not be defined

    bidderDao.notify(InitCache)

    val expectedBidder = Some(
      Bidder(
        1,
        "bidder",
        "endpoint"))

    val bidder = bidderDao.get(1)

    bidder shouldBe expectedBidder
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

    val expectedForDeleteBidder = Some(
      Bidder(
        3,
        "fordelete",
        "fordelete"))

    val forDeleteBidder = bidderDao.get(3)

    forDeleteBidder shouldBe expectedForDeleteBidder

    bidderDao.notify(UpdateCache)
    val deletedBidder = bidderDao.get(3)

    deletedBidder should not be defined
  }

  it should "load inserted bidder by ID correctly after cache update" in {
    val bidderDao = inject[BidderDao]

    bidderDao.notify(InitCache)
    executeQuery("bidder-insert.xml", Insert)

    val notFoundBidder = bidderDao.get(4)
    notFoundBidder should not be defined

    bidderDao.notify(UpdateCache)

    val expectedBidder = Some(
      Bidder(
        4,
        "insertedbidder",
        "insertedendpoint"))

    val bidder = bidderDao.get(4)
    bidder shouldBe expectedBidder
  }
}
