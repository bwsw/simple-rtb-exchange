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
    * @param responses bid responses, taking part in auction
    * @return Some(BidResponse) or None, if winner not found
    */
  def winners(responses: Seq[BidResponse]): Seq[BidResponse]
}

/**
  * Auction implementation.
  *
  * @author Egor Ilchenko
  */
class AuctionImpl extends Auction with Logging {

  override def winners(responses: Seq[BidResponse]): Seq[BidResponse] = {
    val groups = toBidGroups(responses)
    val combinations = maxCombination(groups)
    val result = toBidResponses(combinations)
    result
  }

  /**
    * Returns groups combination with maximum price.
    *
    * @param groups all bid groups
    */
  private def maxCombination(groups: List[BidsGroup]) = (1 to groups.length)
    .view
    .map(combinations(_, groups))
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
  private def combinations(k: Int, groups: List[BidsGroup]): List[List[BidsGroup]] = groups match {
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

        val left = combinations(k - 1, filtered).map(head :: _)
        val right = combinations(k, tail)
        left ::: right
      }
  }

  /**
    * Maps groups of bids to bid responses, preserving source hierarchy.
    *
    * @param groups groups of bids
    * @return sequence of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    */
  def toBidResponses(groups: Seq[BidsGroup]) = groups
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
    * If bids from seatBid can won individually, group is split to smallest groups,
    * each containing one bid.
    *
    * @param responses sequence of [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * @return sequence of groups of bids
    */
  def toBidGroups(responses: Seq[BidResponse]) = responses
    .flatMap { response =>
      response.seatBid
        .flatMap {
          case seatBid@SeatBid(_, _, 0, _) =>
            seatBid.bid.map { bid =>
              BidsGroup(
                response,
                seatBid,
                Seq(bid),
                Seq(bid.impId),
                bid.price)
            }
          case seatBid =>
            Seq(
              BidsGroup(
                response,
                seatBid,
                seatBid.bid,
                seatBid.bid.map(_.impId),
                seatBid.bid.map(_.price).sum))
        }
    }.toList
}

/**
  * Group of bids, which should win or loose only as a group.
  *
  * @param bidResponse associated [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
  * @param seatBid     associated [[com.bitworks.rtb.model.response.SeatBid SeatBid]]
  * @param bids        sequence of [[com.bitworks.rtb.model.response.Bid Bid]]
  * @param impIds      ids of impressions on which bids from the group made
  * @param price       total group price
  * @author Egor Ilchenko
  */
case class BidsGroup(
    bidResponse: BidResponse,
    seatBid: SeatBid,
    bids: Seq[Bid],
    impIds: Seq[String],
    price: BigDecimal)

