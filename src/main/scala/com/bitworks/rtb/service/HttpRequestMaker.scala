package com.bitworks.rtb.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.http.scaladsl.model._
import akka.stream.Materializer
import com.bitworks.rtb.model.http._
import com.bitworks.rtb.service.ContentTypeConversions._

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

  override def make(request: HttpRequestModel) = {
    val entity = request.body match {
      case None => HttpEntity.Empty
      case Some(bytes) => HttpEntity(request.contentType, bytes)
    }

    val akkaRequest = HttpRequest(
      method = request.method match {
        case GET => HttpMethods.GET
        case POST => HttpMethods.POST
      },
      uri = request.uri,
      entity = entity
    )
    val fResponse = Http().singleRequest(akkaRequest)

    val result = for {
      response <- fResponse
      data <- extractData(response)
    } yield HttpResponseModel(
      data._1,
      response.status.intValue,
      data._2)

    result
  }
}
