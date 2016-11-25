package com.bitworks.rtb.service

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bitworks.rtb.model.http._
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import org.easymock.EasyMock
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

import scala.concurrent.duration.FiniteDuration

/**
  * Test for [[com.bitworks.rtb.service.AkkaHttpRequestMaker AkkaHttpRequestMaker]].
  *
  * @author Egor Ilchenko
  */
class AkkaHttpRequestMakerTest extends FlatSpec with BeforeAndAfterEach
  with ScalaFutures with EasyMockSugar with Matchers {

  val port = 6423
  val server = new WireMockServer(port)
  implicit val system = ActorSystem("test")
  implicit val materializer = ActorMaterializer()

  val configuration = mock[Configuration]
  expecting {
    call(configuration.toStrictTimeout).andReturn(FiniteDuration(1, "s")).anyTimes()
  }
  EasyMock.replay(configuration)

  val maker = new AkkaHttpRequestMaker()(system, materializer, configuration)

  override def beforeEach = {
    server.start()
    WireMock.configureFor(port)
  }

  override def afterEach() = {
    server.stop()
  }

  private val responseBody = "somestr"
  private val contentTypeHeader = "Content-Type"
  private val responseHeaderJsonValue = "application/json"
  private val responseStatusCode = 201

  "Akka http request maker" should "make GET requests correctly" in {
    val path = "/get"
    stubFor(
      get(urlEqualTo(path)).willReturn(
        aResponse()
          .withBody(responseBody)
          .withStatus(responseStatusCode)))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      GET,
      None)

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        getRequestedFor(urlEqualTo(path)))

      val bodyAsString = new String(response.body)
      bodyAsString shouldBe responseBody
      response.status shouldBe responseStatusCode
      response.contentType shouldBe Unknown
    }
  }

  it should "make GET requests with empty body correctly" in {
    val path = "/get"
    stubFor(get(urlEqualTo(path)).willReturn(aResponse()))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      GET,
      None)

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        getRequestedFor(urlEqualTo(path)))

      val bodyAsString = new String(response.body)
      bodyAsString shouldBe ""
    }
  }

  it should "make POST requests correctly" in {
    val path = "/post"
    stubFor(
      post(urlEqualTo(path)).willReturn(
        aResponse()
          .withBody(responseBody)
          .withHeader(contentTypeHeader, responseHeaderJsonValue)
          .withStatus(responseStatusCode)))

    val uri = s"http://localhost:$port$path"
    val bytes = responseBody.toCharArray.map(_.toByte)
    val request = HttpRequestModel(
      uri,
      POST,
      Some(bytes),
      Json)

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        postRequestedFor(urlEqualTo(path))
          .withHeader(contentTypeHeader, equalTo(responseHeaderJsonValue))
          .withRequestBody(equalTo(responseBody)))

      val bodyAsString = new String(response.body)
      bodyAsString shouldBe responseBody
      response.status shouldBe responseStatusCode
      response.contentType shouldBe Json
    }
  }

  it should "make POST requests without body correctly" in {
    val path = "/post"
    stubFor(
      post(urlEqualTo(path)).willReturn(
        aResponse()
          .withBody(responseBody)
          .withStatus(responseStatusCode)))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      POST,
      None)

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        postRequestedFor(urlEqualTo(path))
          .withRequestBody(equalTo("")))

      val bodyAsString = new String(response.body)
      bodyAsString shouldBe responseBody
      response.status shouldBe responseStatusCode
    }
  }

}
