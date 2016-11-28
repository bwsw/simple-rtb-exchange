package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.stream.ActorMaterializer
import com.bitworks.rtb.application.HttpRequestWrapper
import com.bitworks.rtb.model.ad.response.AdResponse
import com.bitworks.rtb.model.message._
import com.bitworks.rtb.service.AkkaHttpRequestMaker._
import com.bitworks.rtb.service.factory.{AdResponseFactory, BidRequestFactory}
import com.bitworks.rtb.service.parser.AdRequestParserFactory
import com.bitworks.rtb.service.writer.AdResponseWriterFactory
import com.bitworks.rtb.service.{Configuration, DataValidationException}
import scaldi.Injector
import scaldi.akka.AkkaInjectable._

/**
  * Main actor to process ad requests.
  *
  * @author Egor Ilchenko
  */
class RequestActor(
    request: HttpRequestWrapper)(
    implicit inj: Injector)
  extends Actor
    with ActorLogging {

  import context.dispatcher

  implicit val materializer = ActorMaterializer()
  val configuration = inject[Configuration]
  val writerFactory = inject[AdResponseWriterFactory]
  val parserFactory = inject[AdRequestParserFactory]
  val factory = inject[BidRequestFactory]
  val adResponseFactory = inject[AdResponseFactory]

  override def receive: Receive = {

    case HandleRequest =>
      log.debug("started request handling")

      request.inner.entity.toStrict(configuration.toStrictTimeout) map {
        entity =>
          val bytes = entity.data.toArray
          log.debug(s"content-type: ${entity.contentType}")
          val parser = parserFactory.getParser(entity.contentType)
          val adRequest = parser.parse(bytes)
          try {
            val bidRequest = factory.create(adRequest)
            val props = BidRequestActor.props(adRequest, bidRequest)
            context.actorOf(props)
          } catch {
            case e: DataValidationException =>
              log.debug("bid request not created")
              val response = adResponseFactory.create(adRequest, e.getError)
              completeRequest(response)
          }
      } onFailure {
        case exc => onError(exc.toString)
      }

    case adResponse: AdResponse =>
      log.debug("ad response received")
      completeRequest(adResponse)
  }

  /**
    * Completes request with ad response.
    *
    * @param response [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]] object
    */
  def completeRequest(response: AdResponse) = {
    log.debug("completing request...")
    val writer = writerFactory.getWriter(response.ct)
    val bytes = writer.write(response)
    request.complete(bytes, response.ct)
  }

  /**
    * Completes request with unsuccessful ad response.
    *
    * @param msg error message
    */
  def onError(msg: String) = {
    log.debug(s"an error occurred: $msg")
    request.fail()
  }
}

object RequestActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.RequestActor RequestActor]]. */
  def props(wrapper: HttpRequestWrapper)(implicit inj: Injector) =
  Props(new RequestActor(wrapper))
}
