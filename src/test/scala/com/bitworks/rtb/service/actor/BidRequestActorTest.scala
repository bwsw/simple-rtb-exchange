package com.bitworks.rtb.service.actor

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.testkit.{ImplicitSender, TestKit}
import com.bitworks.rtb.model.ad
import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder
import com.bitworks.rtb.model.ad.response.{AdResponse, Error}
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message.{BidRequestFail, BidRequestSuccess, SendBidRequest}
import com.bitworks.rtb.model.request.builder.{BidRequestBuilder, ImpBuilder}
import com.bitworks.rtb.model.response.builder._
import com.bitworks.rtb.service.dao.BidderDao
import com.bitworks.rtb.service.factory.AdResponseFactory
import com.bitworks.rtb.service.{Auction, BidRequestMaker, Configuration}
import org.easymock.EasyMock
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers, OneInstancePerTest}
import scaldi.{Injector, Module}

import scala.concurrent.duration.FiniteDuration

trait Values {
  val adImp = ad.request.builder.ImpBuilder("1").build
  val adRequest = AdRequestBuilder("12345", Seq(adImp)).build

  val imp = ImpBuilder(adImp.id).build
  val bidRequest = BidRequestBuilder(adRequest.id, Seq(imp)).build

  val bidder1 = Bidder(1, "bidder1", "bidder1.com")
  val bidder2 = Bidder(2, "bidder2", "bidder2.com")
  val bidder3 = Bidder(3, "bidder3", "bidder3.com")

  val bid1 = BidBuilder("1", imp.id, 1)
    .withAdm("admarkup1")
    .build
  val seatBid1 = SeatBidBuilder(Seq(bid1)).build
  val bidResponse1 = BidResponseBuilder(bidRequest.id, Seq(seatBid1))
    .withBidId(bidder1.id.toString)
    .build
  val bidRequestResult1 = BidRequestSuccess(bidResponse1)

  val bidRequestResult2 = BidRequestFail("fail")

  val bid3 = BidBuilder("1", imp.id, bid1.price + 0.5)
    .withAdm("admarkup3")
    .build
  val seatBid3 = SeatBidBuilder(Seq(bid3)).build
  val bidResponse3 = BidResponseBuilder(bidRequest.id, Seq(seatBid3))
    .withBidId(bidder3.id.toString)
    .build
  val bidRequestResult3 = BidRequestSuccess(bidResponse3)

  val adResponseImp1 = ad.response.Imp(imp.id, bid1.adm.get, 1)
  val adResponse1 = AdResponseBuilder(adRequest.id).withImp(Seq(adResponseImp1)).build

  val adResponseImp3 = ad.response.Imp(imp.id, bid3.adm.get, 1)
  val adResponse3 = AdResponseBuilder(adRequest.id).withImp(Seq(adResponseImp3)).build

  val errorResponse = AdResponseBuilder(adRequest.id)
    .withError(Error(123, "error"))
    .build
}

class BidActorMock(implicit inj: Injector) extends BidActor with Values {
  override def receive: Receive = {
    case SendBidRequest(`bidder1`, `bidRequest`) => sender ! bidRequestResult1
    case SendBidRequest(`bidder2`, `bidRequest`) => sender ! bidRequestResult2
    case SendBidRequest(`bidder3`, `bidRequest`) => sender ! bidRequestResult3
  }
}

object BidActorMock {
  def props(implicit inj: Injector) = Props(new BidActorMock)
}

class WinActorMock(implicit inj: Injector) extends WinActor with Values {
  override def receive: Receive = {
    case `bidResponse1` => sender ! bidResponse1
    case `bidResponse3` => sender ! bidResponse3
  }
}

object WinActorMock {
  def props(implicit inj: Injector) = Props(new WinActorMock)
}


/**
  * Test for [[com.bitworks.rtb.service.actor.BidRequestActor BidRequestActor]].
  *
  * @author Pavel Tomskikh
  */
class BidRequestActorTest
  extends TestKit(ActorSystem("test"))
    with FlatSpecLike
    with Matchers
    with EasyMockSugar
    with ImplicitSender
    with BeforeAndAfterAll
    with OneInstancePerTest
    with Values {

  implicit val materializer = ActorMaterializer()

  val auction = niceMock[Auction]
  expecting {
    auction.winner(Seq(bidResponse1)).andStubReturn(Some(bidResponse1))
    auction.winner(Seq(bidResponse3)).andStubReturn(Some(bidResponse3))
    auction.winner(Seq(bidResponse1, bidResponse3)).andStubReturn(Some(bidResponse3))
    auction.winner(Seq(bidResponse3, bidResponse1)).andStubReturn(Some(bidResponse3))
    auction.winner(Seq()).andStubReturn(None)
    EasyMock.replay(auction)
  }


  val adResponseFactory = niceMock[AdResponseFactory]
  expecting {
    adResponseFactory.create(adRequest, bidResponse1).andStubReturn(adResponse1)
    adResponseFactory.create(adRequest, bidResponse3).andStubReturn(adResponse3)
    adResponseFactory.create(EasyMock.eq(adRequest), EasyMock.anyObject(classOf[Error]))
      .andStubReturn(errorResponse)
    EasyMock.replay(adResponseFactory)
  }

  val configuration = niceMock[Configuration]
  expecting {
    configuration.bidRequestTimeout.andStubReturn(FiniteDuration(10, TimeUnit.MINUTES))
    EasyMock.replay(configuration)
  }

  val predefinedInjector = new Module {
    bind[Auction] toNonLazy auction
    bind[AdResponseFactory] toNonLazy adResponseFactory
    bind[BidRequestMaker] toNonLazy niceMock[BidRequestMaker]
    bind[Configuration] toNonLazy configuration
    bind[BidActor] toProvider new BidActorMock
    bind[WinActor] toProvider new WinActorMock
  }


  val responsesForBidders = Table(
    ("bidders", "adResponse"),
    (Seq(), errorResponse),
    (Seq(bidder1), adResponse1),
    (Seq(bidder2), errorResponse),
    (Seq(bidder1, bidder2), adResponse1),
    (Seq(bidder3), adResponse3),
    (Seq(bidder1, bidder3), adResponse3),
    (Seq(bidder2, bidder3), adResponse3),
    (Seq(bidder1, bidder2, bidder3), adResponse3)
  )

  forAll(responsesForBidders) { (bidders: Seq[Bidder], adResponse: AdResponse) =>
    "BidRequestActor" should s"send ad response for $bidders correctly" in {
      val bidderDao = mock[BidderDao]
      expecting {
        bidderDao.getAll.andStubReturn(bidders)
        EasyMock.replay(bidderDao)
      }
      val injector = new Module {
        bind[BidderDao] toNonLazy bidderDao
      } :: predefinedInjector

      childActorOf(BidRequestActor.props(adRequest, bidRequest)(injector), "bidRequestActor")
      expectMsg(adResponse)
    }
  }

}
