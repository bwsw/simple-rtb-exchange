package com.bitworks.rtb.model.http

/**
  * Body of HTTP response.
  *
  * @param bytes  body representation as byte array
  * @param string body representation as string
  * @author Egor Ilchenko
  */
case class HttpResponseBody(bytes: Array[Byte], string: String)
