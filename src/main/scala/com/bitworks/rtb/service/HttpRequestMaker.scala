package com.bitworks.rtb.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.http.scaladsl.model._
import akka.stream.Materializer
import com.bitworks.rtb.model.http._

import scala.collection.immutable.Seq
import scala.concurrent.Future

/**
  * Http request maker.
  *
  * @author Egor Ilchenko
  */
trait HttpRequestMaker {

  /**
    * Makes HTTP request
    *
    * @param request [[com.bitworks.rtb.model.http.HttpRequestModel HttpRequestModel]]
    * @return [[com.bitworks.rtb.model.http.HttpResponseModel HttpResponseModel]]
    */
  def make(request: HttpRequestModel): Future[HttpResponseModel]
}

/**
  * Http request maker implementation using akka-http.
  *
  * @author Egor Ilchenko
  */
class AkkaHttpRequestMaker(
    implicit system: ActorSystem,
    m: Materializer,
    configuration: Configuration) extends HttpRequestMaker {

  import system.dispatcher

  /**
    * Extracts body from response.
    *
    * @param response HttpResponse
    * @return extracted body as byte array
    */
  private def extractBody(response: HttpResponse) = {
    response.entity.toStrict(configuration.toStrictTimeout) map { strict =>
      HttpResponseBody(strict.data.toArray, strict.data.utf8String)
    }
  }

  private def extractHeader(response: HttpResponse) = {
    response.headers.map(x => HttpHeaderModel(x.name, x.value))
  }


  override def make(request: HttpRequestModel) = {
    val entity = request.body match {
      case None => HttpEntity.Empty
      case Some(bytes) => HttpEntity.apply(bytes)
    }

    val headers = request.headers.map { case HttpHeaderModel(key, value) =>
      HttpHeader.parse(key, value) match {
        case Ok(header, _) => header
        case _ => throw new RuntimeException
      }
    }

    val akkaRequest = HttpRequest(
      method = request.method match {
        case GET => HttpMethods.GET
        case POST => HttpMethods.POST
      },
      uri = request.uri,
      entity = entity,
      headers = headers.toList
    )
    val fResponse = Http().singleRequest(akkaRequest)

    val result = for {
      response <- fResponse
      body <- extractBody(response)
    } yield HttpResponseModel(body, response.status.intValue, extractHeader(response))
    result
  }
}
