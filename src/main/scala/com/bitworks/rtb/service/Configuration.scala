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
  private val conf = ConfigFactory.load()

  /** Cache updating interval */
  val cacheUpdateInterval = FiniteDuration(
    conf.getDuration(
      "rtb-exchange.cache-update-interval")
      .toMillis, TimeUnit.MILLISECONDS)
}
