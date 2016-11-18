package com.bitworks.rtb.model.http

/**
  *
  *
  * @author Egor Ilchenko
  */
case class HttpRequestModel(
    uri: String,
    method: Method = Get,
    body: Option[Array[Byte]] = None,
    headers: Option[Seq[(String, String)]] = None)
