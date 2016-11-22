package com.bitworks.rtb.service

import com.bitworks.rtb.model.response.builder.{BidResponseBuilder, SeatBidBuilder}
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
  def winner(responses: Seq[BidResponse]): Option[BidResponse]
}

/**
  * Dummy auction implementation.
  */
class AuctionImpl extends Auction with Logging {
  override def winner(responses: Seq[BidResponse]) : Option[BidResponse] = {
    log.debug("auction started!")
    log.debug(s"bid responses: $responses")
    val groups = getModel(responses)
    log.debug(s"groups: $groups")
    val comb = maxCombination(groups.toList)
    log.debug(s"max comb $comb")
    val response = getResponse(comb)
    log.debug(s"response: $response")
    Some(response)
  }

  private def maxCombination(groups: List[BidGroup]) = {
    val res = (1 to groups.length)
      .flatMap(x => combinations(x, groups))
        .map{x =>
          log.debug(s"possible comb: $x")
          x
        }
      .filter(_.nonEmpty)
      .reduceLeft { (left, right) =>
        val sumLeft = if (left.isEmpty) {
          BigDecimal(0)
        }
        else {
          left.map(_.price).sum
        }
        val sumRight = if (right.isEmpty) {
          BigDecimal(0)
        }
        else {
          right.map(_.price).sum
        }
        if (sumLeft > sumRight)
          left
        else
          right
      }

    res
  }

  private var currentMax = BigDecimal(0)

  private def combinations(k: Int, list: List[BidGroup]): List[List[BidGroup]] = {

    val result = list match {
      case Nil => Nil
      case head :: xs =>
        if (k <= 0 || k > list.length) {
          Nil
        }
        else if (k == 1) {
          list.map(List(_))
        }
        else {
          val filteredXs = xs.filter { elem =>
            !elem.impIds.exists(bb => head.impIds.contains(bb))
          }

          val left = combinations(k - 1, filteredXs).map(head :: _)

          val right = combinations(k, xs)
          left ::: right
        }
    }
    result
  }

  private def getResponse(models: Seq[BidGroup]) = {
    val seatBids = models
      .collect { case m: BidGroup => m }
      .groupBy(_.seatBid)
      .map { case (seatBid, bidGroups) =>
        val builder = SeatBidBuilder(bidGroups.flatMap(_.bids))
        if (seatBid.seat.isDefined) {
          builder.withSeat(seatBid.seat.get)
        }
        builder.build
      }.toSeq

    BidResponseBuilder("empty", seatBids).build
  }

  private def getModel(responses: Seq[BidResponse]): Seq[BidGroup] = {
    val groups = responses
      .flatMap(_.seatBid)
      .filter(_.group == 1)
      .map { x =>
        BidGroup(
          x,
          x.bid,
          x.bid.map(_.impId),
          x.bid.map(_.price).foldLeft(BigDecimal(0))((le, re) => le + re))
      }

    val groupedSingles = responses
      .flatMap(_.seatBid)
      .filter(_.group == 0)
      .flatMap(x => x.bid.map((_, x)))
      .map { case (bid, seatBid) =>
        BidGroup(seatBid, Seq(bid), Seq(bid.impId), bid.price)
      }

    groups ++ groupedSingles
  }
}



case class BidGroup(
    seatBid: SeatBid,
    bids: Seq[Bid],
    impIds: Seq[String],
    price: BigDecimal)
