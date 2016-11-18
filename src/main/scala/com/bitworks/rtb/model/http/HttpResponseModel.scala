package com.bitworks.rtb.model.http

/**
  *
  *
  * @author Egor Ilchenko
  */
case class HttpResponseModel(body: (Array[Byte], String), headers: Option[Seq[(String, String)]] = None)
