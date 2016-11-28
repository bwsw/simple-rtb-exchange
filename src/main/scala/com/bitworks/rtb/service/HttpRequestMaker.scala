package com.bitworks.rtb.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.http.scaladsl.model.{HttpEntity, _}
import akka.stream.Materializer
import com.bitworks.rtb.model.http._

import scala.concurrent.Future

/**
  * Http request maker.
  *
  * @author Egor Ilchenko
  */
trait HttpRequestMaker {

  /**
    * Makes HTTP request.
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
    * Extracts data from response.
    *
    * @param response HttpResponse
    * @return extracted body and content type
    */
  private def extractData(response: HttpResponse) = {
    response.entity.toStrict(configuration.toStrictTimeout) map { strict =>
      (strict.data.toArray, strict.contentType)
    }
  }

  /**
    * Extracts headers from response.
    *
    * @param response HttpResponse
    * @return extracted header models
    */
  private def extractHeaders(response: HttpResponse) = {
    response.headers.map(x => HttpHeaderModel(x.name, x.value))
  }

  override def make(request: HttpRequestModel) = {
    val entity = request.body match {
      case None => HttpEntity(request.contentType, Array.emptyByteArray)
      case Some(bytes) => HttpEntity(request.contentType, bytes)
    }

    val headers = request.headers.map { case h@HttpHeaderModel(key, value) =>
      HttpHeader.parse(key, value) match {
        case Ok(header, _) => header
        case _ => throw new DataValidationException(s"cannot parse $h")
      }
    }.toList

    val akkaRequest = HttpRequest(
      method = request.method match {
        case GET => HttpMethods.GET
        case POST => HttpMethods.POST
      },
      uri = request.uri,
      entity = entity,
      headers = headers
    )
    val fResponse = Http().singleRequest(akkaRequest)

    for {
      response <- fResponse
      data <- extractData(response)
    } yield HttpResponseModel(
      data._1,
      response.status.intValue,
      data._2,
      extractHeaders(response))
  }
}

