package com.bitworks.rtb.service.actor

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.TestActorRef
import akka.util.Timeout
import com.bitworks.rtb.model.db.Bidder
import com.bitworks.rtb.model.message.{BidRequestFail, BidRequestSuccess, SendBidRequest}
import com.bitworks.rtb.model.request.builder.BidRequestBuilder
import com.bitworks.rtb.model.response.builder.BidResponseBuilder
import com.bitworks.rtb.service.{BidRequestMaker, Configuration}
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}
import scaldi.Module

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Test for [[com.bitworks.rtb.service.actor.BidActor]].
  *
  * @author Egor Ilchenko
  */
class BidActorTest extends FlatSpec with MockFactory with OneInstancePerTest
  with ScalaFutures with Matchers {

  val response = BidResponseBuilder("id", Seq.empty).build

  val bidRequestMakerStub = stub[BidRequestMaker]
  (bidRequestMakerStub.send _).when(*, *).returns(Future.successful(response))

  val configuration = stub[Configuration]
  (configuration.bidRequestTimeout _).when().returns(1.second)

  implicit val injector = new Module {
    bind[BidRequestMaker] toNonLazy bidRequestMakerStub
    bind[Configuration] toNonLazy configuration
  }

  implicit val system = ActorSystem()
  implicit val timeout: Timeout = 1.second

  "BidActor" should "receive bid request and send it to bidder" in {

    val actorRef = TestActorRef(new BidActor)

    val bidder = Bidder(1, "name", "endpoint")
    val request = BidRequestBuilder("id", Seq.empty).build

    Await.ready(actorRef ? SendBidRequest(bidder, request), 1.second)

    (bidRequestMakerStub.send _).verify(bidder, request).once
  }

  it should "receive bid response from bidder and send it back" in {

    val actorRef = TestActorRef(new BidActor)

    val bidder = Bidder(1, "name", "endpoint")
    val request = BidRequestBuilder("id", Seq.empty).build

    val future = actorRef ? SendBidRequest(bidder, request)

    Await.ready(future, 1.second)

    val value = future.value.get.toOption

    value shouldBe Some(BidRequestSuccess(response))
  }

  it should "send back failure response if response failed" in {
    val exception = new RuntimeException("message")

    val bidRequestMakerStub = stub[BidRequestMaker]
    (bidRequestMakerStub.send _).when(*, *).returns(Future.failed(exception))

    val module = new Module {
      bind[BidRequestMaker] toNonLazy bidRequestMakerStub
    } :: injector


    val actorRef = TestActorRef(new BidActor()(module))

    val bidder = Bidder(1, "name", "endpoint")
    val request = BidRequestBuilder("id", Seq.empty).build

    val future = actorRef ? SendBidRequest(bidder, request)
    Await.ready(future, 1.second)

    val value = future.value.get.toOption

    value shouldBe Some(BidRequestFail(exception.getMessage))
  }
}
