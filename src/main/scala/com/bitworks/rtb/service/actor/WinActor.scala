package com.bitworks.rtb.service.actor

import java.util.concurrent.TimeoutException

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.after
import com.bitworks.rtb.model.message.SendWinNotice
import com.bitworks.rtb.model.request.BidRequest
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
    case SendWinNotice(request, responses) =>
      val localSender = sender

      val fBidResponsesWithAdm = process(responses, request)
      fBidResponsesWithAdm.onSuccess { case bidResponsesWithAdm =>
        localSender ! bidResponsesWithAdm
      }
  }

  /**
    * Sends win notices and gets ad markup if needed for each bid in responses.
    *
    * @param responses sequence of [[com.bitworks.rtb.model.response.BidResponse BidResponses]]
    * @param request   [[com.bitworks.rtb.model.request.BidRequest BidRequest]]
    * @return sequence of [[com.bitworks.rtb.model.response.BidResponse BidResponses]] with
    *         ad markups filled in
    */
  def process(responses: Seq[BidResponse], request: BidRequest) = {
    val fBidResponses = responses.map { response =>
      val fSeatBids = response.seatBid.map { seatBid =>
        val fBids = seatBid.bid.map(bid => proccessBid(request, response, seatBid, bid))
        val sequenced = Future.sequence(fBids)
        sequenced.map { bids =>
          if (bids != seatBid.bid) seatBid.copy(bid = bids)
          else seatBid
        }
      }
      val sequenced = Future.sequence(fSeatBids)
      sequenced.map { seatBids =>
        if (seatBids != response.seatBid) response.copy(seatBid = seatBids)
        else response
      }
    }
    Future.sequence(fBidResponses)
  }

  /**
    * Sends win notice or gets ad markup for the bid.
    *
    * @param request  [[com.bitworks.rtb.model.request.BidRequest BidRequest]]
    * @param response [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * @param seatBid  [[com.bitworks.rtb.model.response.SeatBid SeatBid]]
    * @param bid      [[com.bitworks.rtb.model.response.Bid Bid]]
    * @return [[com.bitworks.rtb.model.response.Bid Bid]] with ad markup filled in
    */
  def proccessBid(
      request: BidRequest,
      response: BidResponse,
      seatBid: SeatBid,
      bid: Bid): Future[Bid] = {
    bid.nurl match {
      case Some(nurl) =>

        val preparedNurl = requestMaker.replaceMacros(
          nurl,
          request,
          response,
          seatBid,
          bid)

        bid.adm match {
          case Some(_) => sendWinNotice(bid, preparedNurl)
          case None => getBidWithAdm(bid, preparedNurl)
        }
      case None => Future.successful(bid)
    }
  }

  /**
    * Sends win notice to bid.
    *
    * @param bid          [[com.bitworks.rtb.model.response.Bid Bid]]
    * @param preparedNurl ready to request win notice URL
    */
  def sendWinNotice(
      bid: Bid,
      preparedNurl: String): Future[Bid] = {
    log.debug(s"""sending win notice to "$preparedNurl"""")

    requestMaker.sendWinNotice(preparedNurl)
    Future.successful(bid)
  }

  /**
    * Returns [[com.bitworks.rtb.model.response.Bid Bid]] with admarkup.
    *
    * @param bid          [[com.bitworks.rtb.model.response.Bid Bid]]
    * @param preparedNurl ready to request win notice URL
    */
  def getBidWithAdm(
      bid: Bid,
      preparedNurl: String): Future[Bid] = {
    log.debug(s"""getting ad markup from "$preparedNurl"""")

    val fAdMarkup = requestMaker.getAdMarkup(preparedNurl).map(adm => Some(adm))
    val fTimeout = after(duration = config.winNoticeTimeout, using = context.system.scheduler)(
      Future.failed(new TimeoutException()))

    val fAdMarkupWithTimeout = Future.firstCompletedOf(Seq(fAdMarkup, fTimeout))
      .recover {
        case e: Throwable =>
          log.info(s"""getting admarkup for "$preparedNurl" failed with $e""")
          None
      }

    fAdMarkupWithTimeout.map { adm =>
      log.debug(s"""admarkup for "$preparedNurl" received "$adm""""")
      bid.copy(adm = adm)
    }
  }
}

object WinActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.WinActor WinActor]]. */
  def props(implicit inj: Injector) = {
    Props(new WinActor)
  }
}
