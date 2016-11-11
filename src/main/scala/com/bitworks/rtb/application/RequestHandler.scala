package com.bitworks.rtb.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.bitworks.rtb.model.message.HandleRequest
import com.bitworks.rtb.service.actor.{BidActor, RequestActor, WinActor}
import com.bitworks.rtb.service.{Configuration, Logging}
import scaldi.Injectable._
import scaldi.Injector

import scala.concurrent.Future

/**
  * Handler for ad requests.
  *
  * @author Egor Ilchenko
  */
class RequestHandler(conf: Configuration)(implicit val inj: Injector) extends Logging {

  implicit val system = inject[ActorSystem]
  implicit val materializer = ActorMaterializer()

  private val winActor = system.actorOf(WinActor.props)
  private val bidActor = system.actorOf(BidActor.props)

  val asyncHandler = {
    r: HttpRequest => r match {

      case HttpRequest(HttpMethods.POST, Uri.Path("/"), _, _, _) =>
        HttpRequestWrapper.complete(r) { wrapper =>
          val props = RequestActor.props(bidActor, winActor, wrapper)
          system.actorOf(props) ! HandleRequest
        }
      case _ =>
        Future.successful(HttpResponse(StatusCodes.NotFound))
    }
  }

  /** Starts HTTP listener. */
  def run() = {
    val serverSource = Http().bind(conf.interface, conf.port)

    val bindingFuture = serverSource.to(
      Sink.foreach { connection =>
        log.debug("accepted new connection from " + connection.remoteAddress)
        connection.handleWithAsyncHandler(asyncHandler)
      }).run()

    log.info(s"server online at http://${conf.interface}:${conf.port}/")
  }
}
