package com.bitworks.rtb.service.actor

import java.util.concurrent.TimeoutException

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.after
import com.bitworks.rtb.model.message.SendWinNotice
import com.bitworks.rtb.model.response.{Bid, BidResponse, SeatBid}
import com.bitworks.rtb.service.{Configuration, WinNoticeRequestMaker}
import scaldi.Injectable._
import scaldi.Injector

import scala.concurrent.Future


/**
  * Actor responsible for sending win notice and getting ad markup.
  *
  * @author Egor Ilchenko
  */
class WinActor(implicit injector: Injector) extends Actor with ActorLogging {
  val requestMaker = inject[WinNoticeRequestMaker]
  val config = inject[Configuration]

  import context.dispatcher

  override def receive: Receive = {
    case SendWinNotice(request, response) =>
      val preparedResponse = requestMaker.prepareResponse(response, request)
      val seatBids = preparedResponse.seatBid
      log.debug(s"seatBids after preparation: $seatBids")

      sendWinNotices(seatBids)

      val localSender = sender
      val fBidsWithAdm = getBidsWithAdm(seatBids)
      fBidsWithAdm.onSuccess { case bidsWithAdm =>
        log.debug(
          s"adms received\n" +
            s"\tsuccessfully: ${bidsWithAdm.count(_._2.isDefined)}\n" +
            s"\twith error: ${bidsWithAdm.count(_._2.isEmpty)}")

        val responseWithAdm = updateResponseWithAdm(preparedResponse, bidsWithAdm)
        localSender ! responseWithAdm
      }
  }

  /**
    * Sends win notices using fire and forget pattern.
    *
    * @param seatBids sequence of [[com.bitworks.rtb.model.response.SeatBid SeatBid]]
    */
  def sendWinNotices(seatBids: Seq[SeatBid]) = {
    val winNoticeUrls = seatBids
      .flatMap(x => x.bid)
      .filter(x => x.adm.isDefined && x.nurl.isDefined)
      .flatMap(_.nurl)
    winNoticeUrls.foreach(requestMaker.sendWinNotice)

    log.debug(s"Win notices sended to $winNoticeUrls")
  }

  /**
    * Sends win notices and gets ad markup from bidder.
    *
    * @param seatBids sequence of [[com.bitworks.rtb.model.response.SeatBid SeatBid]]
    * @return sequence of tuples ([[com.bitworks.rtb.model.response.Bid Bid]],
    *         Option [[com.bitworks.rtb.model.response.Bid Bid]]), where first element
    *         is source Bid and second element is some Bid with ad markup or None, if
    *         getting of ad markup failed
    */
  def getBidsWithAdm(seatBids: Seq[SeatBid]) = {
    val bidsWithoutAdm = seatBids
      .flatMap(x => x.bid)
      .filter(x => x.adm.isEmpty && x.nurl.isDefined)

    log.debug(s"adm needed for ${bidsWithoutAdm.length} bids, getting...")

    val timeout = after(
      duration = config.winNoticeTimeout,
      using = context.system.scheduler
    )(Future.failed(new TimeoutException))

    val fBidsWithAdm = bidsWithoutAdm.map { bid =>
      Future
        .firstCompletedOf(
          Seq(
            requestMaker.getAdMarkup(bid.nurl.get),
            timeout)).map { body =>
        (bid, Some(bid.copy(adm = Some(body))))
      }.recover { case _ =>
        log.error(s"getting ad markup failed for bid: $bid")
        (bid, None)
      }
    }
    Future.sequence(fBidsWithAdm)
  }


  /**
    * Updates [[com.bitworks.rtb.model.response.BidResponse BidResponse]] with Bids that conatains
    * ad markup.
    *
    * @param response     [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * @param replacements replacements for Bids, where first element
    *                     is source Bid and second element is some Bid with ad markup or None,
    *                     if getting of ad markup failed
    * @return updated [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    *         with missing ad markups
    */
  def updateResponseWithAdm(response: BidResponse, replacements: Seq[(Bid, Option[Bid])]) = {
    val seatBids = response.seatBid.map { seatBid =>
      val bids = seatBid.bid.flatMap { bid =>
        replacements.find(_._1 == bid) match {
          case Some((_, replacement)) => replacement
          case None => Some(bid)
        }
      }
      seatBid.copy(bid = bids)
    }
    response.copy(seatBid = seatBids)
  }

}

object WinActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.WinActor WinActor]]. */
  def props(implicit inj: Injector) = {
    Props(new WinActor)
  }
}
