package com.bitworks.rtb.application

import akka.http.scaladsl.model.HttpHeader.ParsingResult.{Error, Ok}
import akka.http.scaladsl.model.{HttpHeader, HttpRequest, HttpResponse}
import com.bitworks.rtb.model.http.ContentType

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
  def complete(bytes: Array[Byte], ct: ContentType) = {
    val header = HttpHeader.parse(ct.header.name, ct.header.value) match {
      case Ok(h, _) => p.success(HttpResponse().withEntity(bytes).withHeaders(h))
      case _ => fail()
    }
  }

  /**
    * Completes HttpRequest with exception.
    *
    * @param throwable throwed exception
    */
  def fail(throwable: Option[Throwable] = None) = {
    throwable match {
      case Some(t) => p.failure(t)
      case None => p.failure(new RuntimeException)
    }
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
