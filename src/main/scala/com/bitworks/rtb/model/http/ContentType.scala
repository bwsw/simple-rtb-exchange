package com.bitworks.rtb.model.http

/**
  * HTTP request content type.
  *
  * @author Egor Ilchenko
  */
trait ContentType {
  def header: HttpHeaderModel
}

/**
  * JSON content type.
  *
  * @author Egor Ilchenko
  */
case object JSON extends ContentType {
  override def header = HttpHeaderModel("Content-Type", "application/json")
}
