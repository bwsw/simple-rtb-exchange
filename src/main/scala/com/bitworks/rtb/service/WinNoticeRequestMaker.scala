package com.bitworks.rtb.service

import akka.actor.ActorSystem
import com.bitworks.rtb.model.http.{HttpRequestModel, HttpResponseModel}
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.response.{Bid, BidResponse, SeatBid}

import scala.concurrent.Future

/**
  * Win notice request maker.
  *
  * @author Egor Ilchenko
  */
trait WinNoticeRequestMaker {

  /**
    * Sends win notice to given URL.
    *
    * @param nurl win notice URL
    */
  def sendWinNotice(nurl: String): Future[HttpResponseModel]

  /**
    * Sends win notice to given URL and returns ad markup.
    *
    * @param nurl win notice URL
    * @return ad markup
    */
  def getAdMarkup(nurl: String): Future[String]

  /**
    * Replaces macros in text with appropriate data.
    *
    * @param text     text with macros
    * @param request  [[com.bitworks.rtb.model.request.BidRequest BidRequest]]
    * @param response [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * @param seatBid  [[com.bitworks.rtb.model.response.SeatBid SeatBid]]
    * @param bid      [[com.bitworks.rtb.model.response.Bid Bid]]
    * @return win notice URL with replaced macros
    */
  def replaceMacros(
      text: String,
      request: BidRequest,
      response: BidResponse,
      seatBid: SeatBid,
      bid: Bid): String
}

/**
  * Win notice request maker implementation.
  *
  * @author Egor Ilchenko
  */
class WinNoticeRequestMakerImpl(
    httpRequestMaker: HttpRequestMaker,
    system: ActorSystem) extends WinNoticeRequestMaker {

  import system.dispatcher

  override def sendWinNotice(nurl: String) = {
    httpRequestMaker.make(HttpRequestModel(nurl))
  }

  override def getAdMarkup(nurl: String) = {
    httpRequestMaker.make(HttpRequestModel(nurl))
      .map { response =>
        if(response.status > 299){
          throw new RuntimeException("incorrect status code")
        }
        new String(response.body)
      }
  }

  override def replaceMacros(
      text: String,
      request: BidRequest,
      response: BidResponse,
      seatBid: SeatBid,
      bid: Bid) = {
    val subs = Seq(
      "${AUCTION_ID}" -> request.id,
      "${AUCTION_BID_ID}" -> bid.id,
      "${AUCTION_IMP_ID}" -> bid.impId,
      "${AUCTION_SEAT_ID}" -> seatBid.seat.getOrElse(""),
      "${AUCTION_AD_ID}" -> bid.adId.getOrElse(""),
      "${AUCTION_PRICE}" -> bid.price.toString,
      "${AUCTION_CURRENCY}" -> response.cur
    )
    replace(text, subs)
  }

  /**
    * Replaces all occurrences of keys with values.
    *
    * @param str          input string
    * @param replacements replacements in key-value format
    * @return string with replaced keys
    */
  def replace(
      str: String,
      replacements: Seq[(String, String)]): String = replacements match {
    case Nil => str
    case (key, value) :: tail =>
      replace(str.replaceAllLiterally(key, value), tail)
  }
}
