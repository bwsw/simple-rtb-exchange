package com.bitworks.rtb.service

import java.util.concurrent.TimeUnit

import com.bitworks.rtb.model.http.{Avro, ContentTypeModel, Json, Protobuf}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

/**
  * Access point to application configuration.
  *
  * @author Egor Ilchenko
  */
class Configuration {

  private val conf = ConfigFactory.load().getConfig("rtb-exchange")

  /** Cache updating interval. */
  def cacheUpdateInterval = FiniteDuration(
    conf.getDuration(
      "cache-update-interval")
      .toMillis, TimeUnit.MILLISECONDS)

  /** Listener interface. */
  def interface = conf.getString("interface")

  /** Listener port. */
  def port = conf.getInt("port")

  /** Bid request timeout. */
  def bidRequestTimeout = FiniteDuration(
    conf.getDuration(
      "bid-request-timeout")
      .toMillis, TimeUnit.MILLISECONDS)

  /** Win notice timeout. */
  def winNoticeTimeout = FiniteDuration(
    conf.getDuration(
      "win-notice-timeout")
      .toMillis, TimeUnit.MILLISECONDS)

  /* Bid request content type. **/
  def bidRequestContentType: ContentTypeModel = conf.getString("bid-request-content-type") match {
    case "json" => Json
    case "avro" => Avro
    case "protobuf" => Protobuf
    case s => throw new Exception(s"unknown bid request content type in config: $s")
  }

  /** Auction timeout. */
  def auctionTimeout = FiniteDuration(
    conf.getDuration(
      "auction-timeout")
      .toMillis, TimeUnit.MILLISECONDS)


  /** Time added to auction timeout. */
  def additionalAuctionTime = 20.millisecond

  /** Timeout for converting HttpEntity to strict Entity. */
  def toStrictTimeout = 1.second

  /** Error messages configuration for error codes. */
  val errorMessages = ConfigFactory.parseResources(conf.getString("error-messages-file"))
}
