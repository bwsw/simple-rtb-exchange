package com.bitworks.rtb.service.actor

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import com.bitworks.rtb.model.ad
import com.bitworks.rtb.model.ad.request.builder.AdRequestBuilder
import com.bitworks.rtb.model.ad.response.builder.AdResponseBuilder
import com.bitworks.rtb.model.ad.response.{AdResponse, Error}
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.http.Json
import com.bitworks.rtb.model.message._
import com.bitworks.rtb.model.request.builder.{BidRequestBuilder, ImpBuilder}
import com.bitworks.rtb.model.response.builder._
import com.bitworks.rtb.service.dao.BidderDao
import com.bitworks.rtb.service.factory.AdResponseFactory
import com.bitworks.rtb.service.{Auction, BidRequestMaker, Configuration, WinNoticeRequestMaker}
import org.easymock.EasyMock
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers, OneInstancePerTest}
import scaldi.{Injector, Module}

import scala.concurrent.duration._

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
    with OneInstancePerTest {

  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  val adImp = ad.request.builder.ImpBuilder("1").build
  val adRequest = AdRequestBuilder("12345", Seq(adImp), Json).build

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
  val adResponse1 = AdResponseBuilder(adRequest.id, adRequest.ct).withImp(Seq(adResponseImp1)).build

  val adResponseImp3 = ad.response.Imp(imp.id, bid3.adm.get, 1)
  val adResponse3 = AdResponseBuilder(adRequest.id, adRequest.ct).withImp(Seq(adResponseImp3)).build

  val errorResponse = AdResponseBuilder(adRequest.id, adRequest.ct)
    .withError(Error(123, "error"))
    .build

  val smallAuctionTimeout = 1.nanos
  val bigAuctionTimeout = 10.seconds

  class BidActorMock(implicit inj: Injector) extends BidActor {

    import scala.concurrent.ExecutionContext.Implicits.global

    val responseTimeout = smallAuctionTimeout * 2

    override def receive: Receive = {
      case SendBidRequest(`bidder1`, `bidRequest`) =>
        context.system.scheduler.scheduleOnce(responseTimeout, sender, bidRequestResult1)
      case SendBidRequest(`bidder2`, `bidRequest`) =>
        context.system.scheduler.scheduleOnce(responseTimeout, sender, bidRequestResult2)
      case SendBidRequest(`bidder3`, `bidRequest`) =>
        context.system.scheduler.scheduleOnce(responseTimeout, sender, bidRequestResult3)
    }
  }

  object BidActorMock {
    def props(implicit inj: Injector) = Props(new BidActorMock)
  }

  val testProbe = TestProbe()

  class BidActorMockForwarder(implicit inj: Injector) extends BidActor {
    override def receive: Receive = {
      case msg: Any => testProbe.ref ! msg
    }
  }

  object BidActorMockForwarder {
    def props(implicit inj: Injector) = Props(new BidActorMockForwarder)
  }

  class WinActorMock(implicit inj: Injector) extends WinActor {
    override def receive: Receive = {
      case SendWinNotice(`bidRequest`, Seq(`bidResponse1`)) =>
        sender ! CreateAdResponse(Seq(bidResponse1))
      case SendWinNotice(`bidRequest`, Seq(`bidResponse3`)) =>
        sender ! CreateAdResponse(Seq(bidResponse3))
    }
  }

  object WinActorMock {
    def props(implicit inj: Injector) = Props(new WinActorMock)
  }

  class WinActorMockForwarder(implicit inj: Injector) extends WinActor {
    override def receive: Receive = {
      case msg: Any => testProbe.ref ! msg
    }
  }

  object WinActorMockForwarder {
    def props(implicit inj: Injector) = Props(new WinActorMockForwarder)
  }

  implicit val materializer = ActorMaterializer()

  val auction = niceMock[Auction]
  expecting {
    auction.winners(Seq(bidResponse1)).andStubReturn(Seq(bidResponse1))
    auction.winners(Seq(bidResponse3)).andStubReturn(Seq(bidResponse3))
    auction.winners(Seq(bidResponse1, bidResponse3)).andStubReturn(Seq(bidResponse3))
    auction.winners(Seq(bidResponse3, bidResponse1)).andStubReturn(Seq(bidResponse3))
    auction.winners(Seq()).andStubReturn(Seq())
    EasyMock.replay(auction)
  }

  val adResponseFactory = niceMock[AdResponseFactory]
  expecting {
    adResponseFactory.create(adRequest, Seq(bidResponse1)).andStubReturn(adResponse1)
    adResponseFactory.create(adRequest, Seq(bidResponse3)).andStubReturn(adResponse3)
    adResponseFactory.create(EasyMock.eq(adRequest), EasyMock.anyObject(classOf[Error]))
      .andStubReturn(errorResponse)
    EasyMock.replay(adResponseFactory)
  }

  val predefinedInjector = new Module {
    bind[Auction] toNonLazy auction
    bind[AdResponseFactory] toNonLazy adResponseFactory
    bind[BidRequestMaker] toNonLazy niceMock[BidRequestMaker]
    bind[WinNoticeRequestMaker] toNonLazy niceMock[WinNoticeRequestMaker]
  }

  "BidRequestActor" should "send ad request to bid actors" in {
    val configuration = niceMock[Configuration]
    expecting {
      configuration.bidRequestTimeout.andStubReturn(bigAuctionTimeout)
      EasyMock.replay(configuration)
    }

    val bidderDao = mock[BidderDao]
    expecting {
      bidderDao.getAll.andStubReturn(Seq(bidder1, bidder2, bidder3))
      EasyMock.replay(bidderDao)
    }

    implicit val injector = new Module {
      bind[BidderDao] toNonLazy bidderDao
      bind[Configuration] toNonLazy configuration
      bind[BidActor] toProvider new BidActorMockForwarder
      bind[WinActor] toProvider new WinActorMock
    } :: predefinedInjector

    childActorOf(
      BidRequestActor.props(adRequest, bidRequest),
      "bidRequestActor") ! HandleRequest

    testProbe.expectMsgAllOf(
      SendBidRequest(bidder1, bidRequest),
      SendBidRequest(bidder2, bidRequest),
      SendBidRequest(bidder3, bidRequest))
  }

  it should "send correct bid response to win bidder after auction" in {
    val configuration = niceMock[Configuration]
    expecting {
      configuration.bidRequestTimeout.andStubReturn(bigAuctionTimeout)
      EasyMock.replay(configuration)
    }

    val bidderDao = mock[BidderDao]
    expecting {
      bidderDao.getAll.andStubReturn(Seq(bidder1, bidder2, bidder3))
      EasyMock.replay(bidderDao)
    }

    implicit val injector = new Module {
      bind[BidderDao] toNonLazy bidderDao
      bind[Configuration] toNonLazy configuration
      bind[BidActor] toProvider new BidActorMock
      bind[WinActor] toProvider new WinActorMockForwarder
    } :: predefinedInjector

    val bidRequestActor = childActorOf(
      BidRequestActor.props(adRequest, bidRequest),
      "bidRequestActor")
    bidRequestActor ! bidRequestResult1
    bidRequestActor ! bidRequestResult2
    bidRequestActor ! bidRequestResult3
    testProbe.expectMsg(SendWinNotice(bidRequest, Seq(bidResponse3)))
  }

  it should "send correct ad response when got winner's bid response" in {
    val configuration = niceMock[Configuration]
    expecting {
      configuration.bidRequestTimeout.andStubReturn(bigAuctionTimeout)
      EasyMock.replay(configuration)
    }

    val bidderDao = mock[BidderDao]
    expecting {
      bidderDao.getAll.andStubReturn(Seq(bidder1, bidder2, bidder3))
      EasyMock.replay(bidderDao)
    }

    implicit val injector = new Module {
      bind[BidderDao] toNonLazy bidderDao
      bind[Configuration] toNonLazy configuration
      bind[BidActor] toProvider new BidActorMock
      bind[WinActor] toProvider new WinActorMock
    } :: predefinedInjector

    childActorOf(
      BidRequestActor.props(adRequest, bidRequest),
      "bidRequestActor") ! CreateAdResponse(Seq(bidResponse1))
    expectMsg(adResponse1)
  }

  it should "send error when bid responses not received from bidders" in {
    val configuration = niceMock[Configuration]
    expecting {
      configuration.bidRequestTimeout.andStubReturn(smallAuctionTimeout)
      EasyMock.replay(configuration)
    }

    val bidderDao = mock[BidderDao]
    expecting {
      bidderDao.getAll.andStubReturn(Seq(bidder1, bidder2, bidder3))
      EasyMock.replay(bidderDao)
    }

    implicit val injector = new Module {
      bind[BidderDao] toNonLazy bidderDao
      bind[Configuration] toNonLazy configuration
      bind[BidActor] toProvider new BidActorMock
      bind[WinActor] toProvider new WinActorMock
    } :: predefinedInjector

    childActorOf(
      BidRequestActor.props(adRequest, bidRequest),
      "bidRequestActor") ! HandleRequest
    expectMsg(errorResponse)
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
    (Seq(bidder1, bidder2, bidder3), adResponse3))

  forAll(responsesForBidders) { (bidders: Seq[Bidder], adResponse: AdResponse) =>
    it should s"send ad response for $bidders correctly" in {
      val configuration = niceMock[Configuration]
      expecting {
        configuration.bidRequestTimeout.andStubReturn(bigAuctionTimeout)
        EasyMock.replay(configuration)
      }

      val bidderDao = mock[BidderDao]
      expecting {
        bidderDao.getAll.andStubReturn(bidders)
        EasyMock.replay(bidderDao)
      }

      implicit val injector = new Module {
        bind[BidderDao] toNonLazy bidderDao
        bind[Configuration] toNonLazy configuration
        bind[BidActor] toProvider new BidActorMock
        bind[WinActor] toProvider new WinActorMock
      } :: predefinedInjector

      childActorOf(
        BidRequestActor.props(adRequest, bidRequest)(injector),
        "bidRequestActor") ! HandleRequest
      expectMsg(adResponse)
    }
  }

}
