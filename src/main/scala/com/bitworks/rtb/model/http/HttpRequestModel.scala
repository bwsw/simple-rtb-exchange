package com.bitworks.rtb.model.http

/**
  * Model of HTTP request.
  *
  * @param uri         request URI
  * @param method      request method (POST, GET)
  * @param body        request body (for POST method)
  * @param contentType request content type
  * @author Egor Ilchenko
  */
case class HttpRequestModel(
    uri: String,
    method: Method = GET,
    body: Option[Array[Byte]] = None,
    contentType: ContentTypeModel = Unknown)
