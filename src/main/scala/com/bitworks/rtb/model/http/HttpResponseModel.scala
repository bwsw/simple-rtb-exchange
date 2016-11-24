package com.bitworks.rtb.model.http

/**
  * Model of HTTP response.
  *
  * @param body    response body as byte array
  * @param status  response status code
  * @param headers response headers
  * @author Egor Ilchenko
  */
case class HttpResponseModel(
    body: Array[Byte],
    status: Int,
    headers: Seq[HttpHeaderModel] = Seq.empty)
