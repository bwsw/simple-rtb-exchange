package com.bitworks.rtb.application

import akka.http.scaladsl.model.headers.Connection
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.Promise

/**
  * Wrapper for HttpRequest.
  *
  * @param inner inner HttpRequest
  * @param p     promise to complete with HttpResponse
  * @author Egor Ilchenko
  */
class HttpRequestWrapper(val inner: HttpRequest, p: Promise[HttpResponse]) {

  /**
    * Completes HttpRequest.
    *
    * @param bytes response body
    */
  def complete(bytes: Array[Byte]) = {
    p.success(HttpResponse().withEntity(bytes))
  }
}

object HttpRequestWrapper {

  /**
    * Creates promise and waits for HttpRequestWrapper completing.
    *
    * @param request HttpRequest
    * @param inner   action to do with HttpRequestWrapper
    */
  def complete(
      request: HttpRequest)(
      inner: HttpRequestWrapper => Unit) = {
    val p = Promise[HttpResponse]()
    inner(new HttpRequestWrapper(request, p))
    p.future
  }
}
