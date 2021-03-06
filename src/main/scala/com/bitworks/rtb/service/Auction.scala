package com.bitworks.rtb.service

import com.bitworks.rtb.model.response.{Bid, BidResponse, SeatBid}

/**
  * Auction between bidders.
  *
  * @author Egor Ilchenko
  */
trait Auction {

  /**
    * Returns won [[com.bitworks.rtb.model.response.BidResponse BidResponse]].
    *
    * @param responses sequence of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    *                  that take part in auction
    * @return sequence of [[com.bitworks.rtb.model.response.BidResponse BidResponse]] containing
    *         best bids
    */
  def winners(responses: Seq[BidResponse]): Seq[BidResponse]
}

/**
  * Auction implementation.
  *
  * @author Egor Ilchenko
  */
class AuctionImpl(configuration: Configuration) extends Auction with Logging {

  val timeout = configuration.auctionTimeout

  override def winners(responses: Seq[BidResponse]): Seq[BidResponse] = {
    val groups = toBidGroups(responses)
    val targetTime = System.currentTimeMillis() + timeout.toMillis
    val combinations = maxCombination(groups, targetTime)
    val result = toBidResponses(combinations)
    result
  }

  /**
    * Returns combination of groups with maximum price.
    *
    * @param groups all bid groups
    */
  private def maxCombination(groups: List[BidGroup], targetTime: Long) = (1 to groups.length)
    .view
    .map(combinations(_, groups, targetTime))
    .takeWhile(_.nonEmpty)
    .flatten
    .toList match {
    case Nil => Nil
    case list => list.maxBy(x => x.map(_.price).sum)
  }

  /**
    * Returns all possible combinations of groups by k elements.
    *
    * @param k      amount of groups in one combination
    * @param groups bid groups to make combinations with
    */
  private def combinations(
      k: Int,
      groups: List[BidGroup],
      targetTime: Long): List[List[BidGroup]] = {
    if (isTimeoutExpired(targetTime)) {
      Nil
    } else {
      groups match {
        case Nil => Nil
        case head :: tail =>
          if (k <= 0 || k > groups.length) {
            Nil
          }
          else if (k == 1) {
            groups.map(List(_))
          }
          else {
            val filtered = tail.filter { elem =>
              !elem.impIds.exists(bb => head.impIds.contains(bb))
            }

            val left = combinations(k - 1, filtered, targetTime).map(head :: _)
            val right = combinations(k, tail, targetTime)
            left ::: right
          }
      }
    }
  }

  /**
    * Maps groups of bids to bid responses, preserving source hierarchy.
    *
    * @param groups groups of bids
    * @return sequence of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    */
  def toBidResponses(groups: Seq[BidGroup]): Seq[BidResponse] = groups
    .groupBy(_.bidResponse)
    .map { case (response, byResponse) =>
      val seatBids = byResponse
        .groupBy(_.seatBid)
        .map { case (seatBid, bySeatBid) =>
          seatBid.copy(bid = bySeatBid.flatMap(_.bids))
        }
      response.copy(seatBid = seatBids.toSeq)
    }.toSeq

  /**
    * Maps bid responses to groups of bids.
    * If bids from seatBid can won individually the group is split to smallest groups each
    * containing one bid.
    *
    * @param responses sequence of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * @return list of groups of bids
    */
  def toBidGroups(responses: Seq[BidResponse]): List[BidGroup] = responses
    .flatMap { response =>
      response.seatBid
        .flatMap { seatBid =>
          if (seatBid.isGrouped) {
            Seq(
              BidGroup(
                response,
                seatBid,
                seatBid.bid,
                seatBid.bid.map(_.impId),
                seatBid.bid.map(_.price).sum))
          } else {
            seatBid.bid.map { bid =>
              BidGroup(
                response,
                seatBid,
                Seq(bid),
                Seq(bid.impId),
                bid.price)
            }
          }
        }
    }.toList

  /**
    * Checks if current time reaches time to stop.
    *
    * @param timeToStop time limit
    * @return true if current time reaches time to stop
    */
  def isTimeoutExpired(timeToStop: Long) = System.currentTimeMillis() >= timeToStop

}

/**
  * A group of bids which should win or loose only as a group.
  *
  * @param bidResponse associated [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
  * @param seatBid     associated [[com.bitworks.rtb.model.response.SeatBid SeatBid]]
  * @param bids        sequence of [[com.bitworks.rtb.model.response.Bid Bid]]
  * @param impIds      ids of impressions on which bids from the group made
  * @param price       total group price
  * @author Egor Ilchenko
  */
case class BidGroup(
    bidResponse: BidResponse,
    seatBid: SeatBid,
    bids: Seq[Bid],
    impIds: Seq[String],
    price: BigDecimal)

