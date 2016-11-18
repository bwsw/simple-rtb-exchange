package com.bitworks.rtb.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import akka.http.scaladsl.model._
import akka.stream.Materializer
import com.bitworks.rtb.model.http.{Get, HttpRequestModel, HttpResponseModel, Post}

import scala.collection.immutable.Seq

import scala.concurrent.Future

/**
  * Http request maker.
  *
  * @author Egor Ilchenko
  */
trait HttpRequestMaker {

  /**
    * Makes POST request.
    *
    * @param uri  endpoint uri
    * @param body request body
    * @return response body as byte array
    */
  def post(uri: String, body: Array[Byte]): Future[Array[Byte]]

  def proccess(request: HttpRequestModel): Future[HttpResponseModel]
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

  override def post(uri: String, body: Array[Byte]) = {
    Http().singleRequest(HttpRequest(uri = uri).withEntity(body))
      .flatMap(extractBody).map(_._1)
  }

  /**
    * Extracts body from response.
    *
    * @param response HttpResponse
    * @return extracted body as byte array
    */
  private def extractBody(response: HttpResponse) = {
    response.entity.toStrict(configuration.toStrictTimeout) map { strict =>
      (strict.data.toArray, strict.data.utf8String)
    }
  }

  private def extractHeader(response: HttpResponse) = {
    response.headers.map(x => (x.name, x.value))
  }


  override def proccess(request: HttpRequestModel) = {
    val entity = request.body match {
      case None => HttpEntity.Empty
      case Some(bytes) => HttpEntity.apply(bytes)
    }

    val headers = request.headers match {
      case None => Seq.empty
      case Some(seq) =>
        seq.map { case (key, value) =>
          HttpHeader.parse(key, value) match {
            case Ok(header, _) => header
            case _ => throw new RuntimeException
          }
        }
    }

    val akkaRequest = HttpRequest(
      method = request.method match {
        case Get => HttpMethods.GET
        case Post => HttpMethods.POST
      },
      uri = request.uri,
      entity = entity,
      headers = headers.toList
    )
    val fResponse = Http().singleRequest(akkaRequest)

    val result = for {
      response <- fResponse
      body <- extractBody(response)
    } yield HttpResponseModel(body, Some(extractHeader(response)))
    result
  }
}
