package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.RoundRobinPool
import akka.stream.ActorMaterializer
import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.ad.response.Error
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message._
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.model.response.BidResponse
import com.bitworks.rtb.service.Auction
import com.bitworks.rtb.service.dao.BidderDao
import com.bitworks.rtb.service.factory.AdResponseFactory
import scaldi.Injector
import scaldi.akka.AkkaInjectable._

import scala.collection.mutable.ListBuffer

/**
  * Main actor to process bid requests.
  *
  * @param adRequest  [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] object
  * @param bidRequest [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object created
  *                   from adRequest
  * @author Pavel Tomskikh
  */
class BidRequestActor(
    adRequest: AdRequest,
    bidRequest: BidRequest)(
    implicit inj: Injector) extends Actor with ActorLogging {

  implicit val materializer = ActorMaterializer()
  val auction = inject[Auction]
  val bidderDao = inject[BidderDao]
  val adResponseFactory = inject[AdResponseFactory]

  val bidders = bidderDao.getAll
  val receivedBidResponses = new ListBuffer[BidRequestResult]

  val bidActorProps = injectActorProps[BidActor]
  val bidRouter = context.actorOf(
    RoundRobinPool(bidders.length)
      .props(bidActorProps), "bidrouter")

  val winActor = injectActorRef[WinActor]

  override def preStart(): Unit = {
    log.debug("started bid request handling")

    bidders match {
      case Seq() => onError("bidders not found")
      case bidders: Seq[Bidder] =>
        bidders.foreach { bidder =>
          bidRouter ! SendBidRequest(bidder, bidRequest)
        }
    }
  }

  override def receive: Receive = {
    case bidRequestResult: BidRequestResult =>
      log.debug(s"bid response received: $bidRequestResult")
      receivedBidResponses.append(bidRequestResult)
      if (receivedBidResponses.size == bidders.length) {
        startAuction()
      }

    case bidResponse: BidResponse =>
      log.debug("bid response received")
      try {
        val response = adResponseFactory.create(adRequest, bidResponse)
        context.parent ! response
      } catch {
        case e: Throwable => onError(e.getMessage)
      }
  }

  /**
    * Starts auction between successful bid responses.
    */
  def startAuction() = {
    log.debug("auction started")
    val successful = receivedBidResponses
      .collect {
        case BidRequestSuccess(response) => response
      }
    log.debug(s"auction participants: ${successful.length}")

    val winner = auction.winner(successful)
    log.debug(s"auction winner: $winner")

    winner match {
      case Some(response) => winActor ! response
      case None => onError("winner not found")
    }
  }

  /**
    * Sends unsuccessful ad response to parent actor.
    *
    * @param msg error message
    */
  def onError(msg: String) = {
    log.debug(s"an error occurred: $msg")
    context.parent ! adResponseFactory.create(adRequest, Error(123, msg))
  }
}

object BidRequestActor {

  /**
    * Returns Props for [[com.bitworks.rtb.service.actor.BidRequestActor BidRequestActor]].
    */
  def props(
      adRequest: AdRequest,
      bidRequest: BidRequest)(
      implicit inj: Injector) = {
    Props(new BidRequestActor(adRequest, bidRequest))
  }
}
