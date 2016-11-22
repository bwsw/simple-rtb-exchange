package com.bitworks.rtb.model.http

/**
  * Model of HTTP request.
  *
  * @param uri     request URI
  * @param method  request method (POST, GET)
  * @param body    request body (for POST method)
  * @param headers request headers
  * @author Egor Ilchenko
  */
case class HttpRequestModel(
    uri: String,
    method: Method = GET,
    body: Option[Array[Byte]] = None,
    headers: Seq[HttpHeaderModel] = Seq.empty)
