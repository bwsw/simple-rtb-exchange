package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.RoundRobinPool
import akka.stream.ActorMaterializer
import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.ad.response.ErrorCode
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message._
import com.bitworks.rtb.model.request.BidRequest
import com.bitworks.rtb.service.dao.BidderDao
import com.bitworks.rtb.service.factory.AdResponseFactory
import com.bitworks.rtb.service.{Auction, Configuration}
import scaldi.Injector
import scaldi.akka.AkkaInjectable._

import scala.collection.mutable.ListBuffer

/**
  * The main actor to process bid requests.
  *
  * @param adRequest  [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] object
  * @param bidRequest [[com.bitworks.rtb.model.request.BidRequest BidRequest]] object created
  *                   from adRequest
  * @author Pavel Tomskikh
  */
class BidRequestActor(
    adRequest: AdRequest,
    bidRequest: BidRequest)
  (implicit inj: Injector) extends Actor with ActorLogging {

  import context.dispatcher

  implicit val materializer = ActorMaterializer()
  val configuration = inject[Configuration]
  val auction = inject[Auction]
  val adResponseFactory = inject[AdResponseFactory]
  val bidders = inject[BidderDao].getAll
  val bidActorProps = injectActorProps[BidActor]
  val receivedBidResponses = new ListBuffer[BidRequestResult]
  val bidRouter = context.actorOf(
    RoundRobinPool(bidders.length)
      .props(bidActorProps), "bidrouter")

  val winActor = injectActorRef[WinActor]

  var auctionStarted = false

  override def receive: Receive = {
    case HandleRequest =>
      log.debug("started bid request handling")

      bidders match {
        case Seq() => onError("bidders not found")
        case _: Seq[Bidder] =>
          bidders.foreach { bidder =>
            bidRouter ! SendBidRequest(bidder, bidRequest)
          }
          context.system.scheduler.scheduleOnce(
            configuration.bidRequestTimeout,
            self,
            StartAuction)
      }

    case bidRequestResult: BidRequestResult =>
      log.debug(s"bid response received: $bidRequestResult")
      receivedBidResponses.append(bidRequestResult)
      if (receivedBidResponses.size == bidders.length) {
        self ! StartAuction
      }

    case CreateAdResponse(responses) =>
      log.debug("bid responses received")
      try {
        val response = adResponseFactory.create(adRequest, responses)
        context.parent ! response
      } catch {
        case e: Throwable => onError(e.getMessage)
      }

    case StartAuction =>
      if (!auctionStarted) {
        auctionStarted = true
        log.debug("auction started")
        val successful = receivedBidResponses.collect {
          case BidRequestSuccess(response) => response
        }
        log.debug(s"auction participants: ${successful.length}")
        val winners = auction.winners(successful)
        log.debug(s"auction winners: $winners")

        winners match {
          case Nil => onError("winner not defined")
          case _ => winActor ! SendWinNotice(bidRequest, winners)
        }
      }
  }

  /**
    * Sends unsuccessful ad response to parent actor.
    *
    * @param msg error message
    */
  def onError(msg: String) = {
    log.debug(s"an error occurred: $msg")
    context.parent ! adResponseFactory.create(adRequest, ErrorCode.NO_AD_FOUND)
  }
}

object BidRequestActor {

  /**
    * Returns Props for [[com.bitworks.rtb.service.actor.BidRequestActor BidRequestActor]].
    */
  def props(adRequest: AdRequest, bidRequest: BidRequest)
    (implicit inj: Injector) = Props(new BidRequestActor(adRequest, bidRequest))
}
