package com.bitworks.rtb.model.http

/**
  * Model of HTTP response.
  *
  * @param body    response body
  * @param status  response status code
  * @param headers response headers
  * @author Egor Ilchenko
  */
case class HttpResponseModel(
    body: HttpResponseBody,
    status: Int,
    headers: Seq[HttpHeaderModel] = Seq.empty)
