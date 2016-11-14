package com.bitworks.rtb.service

import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.FiniteDuration

/**
  * Access point to application configuration.
  *
  * @author Egor Ilchenko
  */
class Configuration {

  private val conf = ConfigFactory.load().getConfig("rtb-exchange")

  /** Cache updating interval */
  val cacheUpdateInterval = FiniteDuration(
    conf.getDuration(
      "cache-update-interval")
      .toMillis, TimeUnit.MILLISECONDS)

  /** Listener interface */
  val interface = conf.getString("interface")

  /** Listener port */
  val port = conf.getInt("port")
}
