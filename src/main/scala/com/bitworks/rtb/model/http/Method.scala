package com.bitworks.rtb.model.http

/**
  * HTTP request method.
  *
  * @author Egor Ilchenko
  */
trait Method

/**
  * HTTP GET method.
  */
case object GET extends Method

/**
  * HTTP POST method.
  */
case object POST extends Method
