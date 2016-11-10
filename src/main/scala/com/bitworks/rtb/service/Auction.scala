package com.bitworks.rtb.service

import com.bitworks.rtb.model.response.BidResponse

/**
  * Auction between bidders.
  *
  * @author Egor Ilchenko
  */
trait Auction{

  /**
    * Returns won [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * @param responses bid responses, taking part in auction
    * @return Some(BidResponse) or None, if winner not found
    */
  def winner(responses: Seq[BidResponse]): Option[BidResponse]
}

/**
  * Dummy auction implementation.
  */
class AuctionImpl extends Auction{
  override def winner(responses: Seq[BidResponse]) = {
    responses match {
      case Seq() => None
      case s: Seq[BidResponse] =>
        Some(s.head)
    }
  }
}
