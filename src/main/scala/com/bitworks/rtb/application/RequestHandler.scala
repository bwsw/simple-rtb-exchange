package com.bitworks.rtb.application

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.bitworks.rtb.model.message.HandleRequest
import com.bitworks.rtb.service.actor.{BidActor, RequestActor, WinActor}
import com.bitworks.rtb.service.{Configuration, Logging}
import scaldi.Injectable._
import scaldi.Injector

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

  private val route = (post & entity(as[Array[Byte]])) { body =>
    RequestContextWrapper.complete { ctx =>
      val props = RequestActor.props(bidActor, winActor, ctx)
      system.actorOf(props) ! HandleRequest(body)
    }
  }

  /** Starts HTTP listener. */
  def run() = {
    Http().bindAndHandle(route, conf.interface, conf.port)

    log.info(s"server online at http://${conf.interface}:${conf.port}/")
  }

}
