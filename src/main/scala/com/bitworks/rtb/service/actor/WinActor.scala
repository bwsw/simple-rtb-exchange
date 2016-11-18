package com.bitworks.rtb.service.actor

import java.util.concurrent.TimeoutException

import akka.actor.Status.Success
import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import com.bitworks.rtb.model.message.SendWinNotice
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.response.{Bid, BidResponse, SeatBid}
import com.bitworks.rtb.service.{Configuration, WinNoticeRequestMaker}
import scaldi.Injector
import scaldi.Injectable._
import akka.pattern.after

import scala.concurrent.{Await, Future}


/**
  * Actor responsible for sending win notice and getting ad request.
  *
  * @author Egor Ilchenko
  */
class WinActor(implicit injector: Injector) extends Actor with ActorLogging {
  val requestMaker = inject[WinNoticeRequestMaker]
  val config = inject[Configuration]

  import context.dispatcher

  override def receive: Receive = {
    case SendWinNotice(request, response) =>
      val seatBids = substituteNurls(request, response)
      log.debug(s"seatBids after nurl subs: $seatBids")

      val winNoticeUrls = seatBids
        .flatMap(x => x.bid)
        .filter(x => x.adm.isDefined && x.nurl.isDefined)
        .flatMap(_.nurl)
      log.debug(s"sending win notices to $winNoticeUrls ...")
      winNoticeUrls.foreach(requestMaker.sendWinNotice)
      log.debug(s"win notices sended")

      val bidsNeedAdm = seatBids
        .flatMap(x => x.bid)
        .filter(x => x.adm.isEmpty && x.nurl.isDefined)
      log.debug(s"adm needed for ${bidsNeedAdm.length} bids, getting...")

      val fBidsWithAdm = bidsNeedAdm.map(getBidWithBody)
      val fSequenced = Future.sequence(fBidsWithAdm)
      val localSender = sender
      fSequenced.onSuccess { case bidsWithAdm =>
        log.debug("adms received")
        log.debug(s"\tsuccessfully: ${bidsWithAdm.count(_._2.isDefined)}")
        log.debug(s"\twith error: ${bidsWithAdm.count(_._2.isEmpty)}")

        val preparedSeatBids = replaceAdms(seatBids, bidsWithAdm)
        val result = response.copy(seatBid = preparedSeatBids)
        log.debug(s"sending response back...")
        localSender ! result
      }
  }

  def replaceAdms(seatBids: Seq[SeatBid], replacements: Seq[(Bid, Option[Bid])]) = {
    seatBids.map { seatBid =>
      val bids = seatBid.bid.flatMap { bid =>
        replacements.find(_._1 == bid) match {
          case None => Some(bid)
          case Some(rep) => rep._2 match {
            case None => None
            case Some(value) => Some(value)
          }
        }
      }
      seatBid.copy(bid = bids)
    }
  }

  val timeout = after(
    duration = config.winNoticeTimeout,
    using = context.system.scheduler
  )(Future.failed(new TimeoutException))

  def getBidWithBody(bid: Bid) = {
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

  def substituteNurls(
      request: BidRequest,
      response: BidResponse) = {
    response.seatBid.map { seatBid =>
      val updatedBids = seatBid.bid.map { bid =>
        bid.copy(
          nurl = bid.nurl match {
            case None => None
            case Some(nurl) => Some(substituteNurl(nurl, request, response, seatBid, bid))
          })
      }
      seatBid.copy(bid = updatedBids)
    }
  }

  def substituteNurl(
      nurl: String,
      request: BidRequest,
      response: BidResponse,
      seatBid: SeatBid,
      bid: Bid) = {
    val subs = Seq(
      "${AUCTION_ID}" -> request.id,
      "${AUCTION_BID_ID}" -> bid.id,
      "${AUCTION_IMP_ID}" -> bid.impId,
      "${AUCTION_SEAT_ID}" -> seatBid.seat.get,
      "${AUCTION_AD_ID}" -> bid.adId.get,
      "${AUCTION_PRICE}" -> bid.price.toString,
      "${AUCTION_CURRENCY}" -> response.cur
    )
    replace(nurl, subs)
  }

  def replace(
      str: String,
      replacements: Seq[(String, String)]): String = replacements match {
    case Nil => str
    case (key, value) :: tl =>
      replace(str.replaceAllLiterally(key, value), tl)
  }
}

object WinActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.WinActor WinActor]]. */
  def props(implicit inj: Injector) = {
    Props(new WinActor)
  }
}
