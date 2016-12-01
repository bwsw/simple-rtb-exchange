package com.bitworks.rtb.service

import com.bitworks.rtb.model.http.{Avro, Json, Protobuf}
import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

/**
  * Test for [[Configuration Configuration]].
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

  it should "load win notice timeout correctly" in {
    System.setProperty("rtb-exchange.win-notice-timeout", "600 milliseconds")

    ConfigFactory.invalidateCaches()
    val conf = new Configuration

    val timeout = conf.winNoticeTimeout

    timeout shouldBe 600.milliseconds
  }

  it should "load bid request content type correctly" in {
    Seq("json" -> Json, "avro" -> Avro, "protobuf" -> Protobuf).foreach { testcase =>
      System.setProperty("rtb-exchange.bid-request-content-type", testcase._1)

      ConfigFactory.invalidateCaches()
      val conf = new Configuration

      val ct = conf.bidRequestContentType

      ct shouldBe testcase._2
    }
  }

  it should "throw exception for incorrect bid request content type" in {
    System.setProperty("rtb-exchange.bid-request-content-type", "INCORRECT")

    ConfigFactory.invalidateCaches()
    val conf = new Configuration

    an[DataValidationException] shouldBe thrownBy {
      val ct = conf.bidRequestContentType
    }
  }

  it should "load auction timeout correctly" in {
    System.setProperty("rtb-exchange.auction-timeout", "600 milliseconds")

    ConfigFactory.invalidateCaches()
    val conf = new Configuration

    val timeout = conf.auctionTimeout

    timeout shouldBe 600.milliseconds
  }
}
