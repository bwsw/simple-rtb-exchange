package com.bitworks.rtb.service

import java.util.concurrent.TimeUnit

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

  /** Timeout for converting HttpEntity to strict Entity. */
  def toStrictTimeout = 1.second
}
