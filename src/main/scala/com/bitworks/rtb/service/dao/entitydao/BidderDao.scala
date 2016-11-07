package com.bitworks.rtb.service.dao.entitydao

import com.bitworks.rtb.service.dao.schema.BidderEntity
import com.bitworks.rtb.service.dao.{BaseDao, CacheHelper, CacheUpdater, DbContext}
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message.{CacheMessage, InitCache, UpdateCache}

/**
  * DAO for [[com.bitworks.rtb.model.db.Bidder Bidder]].
  *
  * @author Egor Ilchenko
  */
trait BidderDao extends BaseDao[Bidder] with CacheHelper[Bidder]

/**
  * DAO implementation for [[com.bitworks.rtb.model.db.Bidder Bidder]].
  *
  * @param ctx     DB context
  * @param updater [[CacheUpdater CacheUpdater]]
  */
class BidderDaoImpl(
    ctx: DbContext,
    val updater: CacheUpdater) extends BidderDao {

  import ctx._

  override def notify(action: CacheMessage) = {
    val bidders = action match {
      case InitCache => ctx.run {
        Schema.bidder
          .filter(!_.deleted)
      }
      case UpdateCache => ctx.run {
        Schema.bidder
          .filter(_.tsversion > lift(tsversion))
      }
    }
    updateCache(bidders, createBidder)
  }

  /**
    * Creates [[com.bitworks.rtb.model.db.Bidder Bidder]] from
    * [[com.bitworks.rtb.service.dao.schema.BidderEntity BidderEntity]]
    *
    * @param entity [[com.bitworks.rtb.service.dao.schema.BidderEntity BidderEntity]]
    * @return created [[com.bitworks.rtb.model.db.Bidder Bidder]]
    */
  private def createBidder(entity: BidderEntity) = {
    Bidder(entity.id, entity.name, entity.endpoint)
  }
}
