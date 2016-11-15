package com.bitworks.rtb.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer

import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Http request maker.
  *
  * @author Egor Ilchenko
  */
trait RequestMaker {

  /**
    * Makes POST request.
    *
    * @param uri  endpoint uri
    * @param body request body
    * @return response body as byte array
    */
  def post(uri: String, body: Array[Byte]): Future[Array[Byte]]
}

/**
  * Http request maker implementation using akka-http.
  *
  * @author Egor Ilchenko
  */
class AkkaHttpRequestMaker(
    implicit system: ActorSystem,
    m: Materializer,
    configuration: Configuration) extends RequestMaker {

  import system.dispatcher

  override def post(uri: String, body: Array[Byte]) = {
    Http().singleRequest(HttpRequest(uri = uri).withEntity(body)).flatMap(extractBody)
  }

  /**
    * Extracts body from response.
    *
    * @param response HttpResponse
    * @return extracted body as byte array
    */
  private def extractBody(response: HttpResponse) = {
    response.entity.toStrict(configuration.toStrictTimeout) map { strict =>
      strict.data.toArray
    }
  }
}
