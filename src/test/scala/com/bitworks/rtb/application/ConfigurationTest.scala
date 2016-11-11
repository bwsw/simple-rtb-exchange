package com.bitworks.rtb.application

import com.bitworks.rtb.service.Configuration
import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

/**
  * Test for [[Configuration Configuration]]
  *
  * @author Egor Ilchenko
  */
class ConfigurationTest extends FlatSpec with Matchers {

  "Configuration" should "load cache update interval correctly" in {
    System.setProperty("rtb-exchange.cache-update-interval", "53 minutes")

    ConfigFactory.invalidateCaches()
    val conf = new Configuration

    val interval = conf.cacheUpdateInterval

    interval shouldBe 53.minutes
  }
}
