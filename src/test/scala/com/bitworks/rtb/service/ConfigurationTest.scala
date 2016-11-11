package com.bitworks.rtb.service

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

  it should "load interface correctly" in {
    val expected = "smth"
    System.setProperty("rtb-exchange.interface", expected)

    ConfigFactory.invalidateCaches()
    val conf = new Configuration

    val interface = conf.interface

    interface shouldBe expected
  }

  it should "load port correctly" in {
    val expected = 213
    System.setProperty("rtb-exchange.port", expected.toString)

    ConfigFactory.invalidateCaches()
    val conf = new Configuration

    val port = conf.port

    port shouldBe expected
  }

  it should "load bid request timeout correctly" in {
    System.setProperty("rtb-exchange.bid-request-timeout", "500 milliseconds")

    ConfigFactory.invalidateCaches()
    val conf = new Configuration

    val timeout = conf.bidRequestTimeout

    timeout shouldBe 500.milliseconds
  }
}
