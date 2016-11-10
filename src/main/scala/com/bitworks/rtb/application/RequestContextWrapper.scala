package com.bitworks.rtb.application

import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}

import scala.concurrent.Promise

/**
  * Wrapper for RequestContext, allow to use tell akka pattern.
  *
  * @author Egor Ilchenko
  */
class RequestContextWrapper(
    ctx: RequestContext,
    promise: Promise[RouteResult]) {
  private implicit val ec = ctx.executionContext

  /**
    * Completes request.
    *
    * @param bytes response body
    */
  def complete(bytes: Array[Byte]): Unit = ctx.complete(bytes).onComplete(promise.complete)

  /**
    * Completes request.
    *
    * @param str response body
    */
  def complete(str: String): Unit = ctx.complete(str).onComplete(promise.complete)

}

object RequestContextWrapper {

  /**
    * Creates promise and waits for RequestContextWrapper complete
    * @param inner
    * @return
    */
  def complete(inner: RequestContextWrapper => Unit): Route = { ctx:
  RequestContext =>
    val p = Promise[RouteResult]()
    inner(new RequestContextWrapper(ctx, p))
    p.future
  }
}
