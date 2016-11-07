package com.bitworks.rtb.application

import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.FiniteDuration

/**
  * Access point to "application.conf"
  *
  * @author Egor Ilchenko
  */
class Configuration {
  private val conf = ConfigFactory.load()

  /** Returns cache updating interval */
  def cacheUpdateInterval = FiniteDuration(
    conf.getDuration(
      "rtb-exchange.cache-update-interval")
      .toMillis, TimeUnit.MILLISECONDS)
}
