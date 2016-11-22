package com.bitworks.rtb.service

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bitworks.rtb.model.http.{GET, HttpHeaderModel, HttpRequestModel, POST}
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import org.easymock.EasyMock
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers, OneInstancePerTest}

import scala.concurrent.duration.FiniteDuration

/**
  * Test for [[com.bitworks.rtb.service.AkkaHttpRequestMaker AkkaHttpRequestMaker]].
  *
  * @author Egor Ilchenko
  */
class AkkaHttpRequestMakerTest extends FlatSpec with BeforeAndAfterEach
  with ScalaFutures with EasyMockSugar with Matchers  {

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
  private val responseHeaderName = "respheadername"
  private val responseHeaderValue = "respheaderval"
  private val requestHeaderName = "reqheadername"
  private val requestHeaderValue = "reqheadervalue"
  private val responseStatusCode = 201

  "Akka http request maker" should "make GET requests correctly" in {
    val path = "/get"
    stubFor(
      get(urlEqualTo(path)).willReturn(
        aResponse()
          .withBody(responseBody)
          .withHeader(responseHeaderName, responseHeaderValue)
          .withStatus(responseStatusCode)))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      GET,
      None,
      Seq(
        HttpHeaderModel(
          requestHeaderName,
          requestHeaderValue)))

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        getRequestedFor(urlEqualTo(path))
          .withHeader(requestHeaderName, equalTo(requestHeaderValue)))

      response.body.string shouldBe responseBody
      response.status shouldBe responseStatusCode
      response.headers should contain(HttpHeaderModel(responseHeaderName, responseHeaderValue))

    }
  }

  it should "make GET requests without headers and with empty body correctly" in {
    val path = "/get"
    stubFor(get(urlEqualTo(path)).willReturn(aResponse()))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      GET,
      None,
      Seq.empty)

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        getRequestedFor(urlEqualTo(path)))

      response.body.string shouldBe ""
    }
  }

  it should "make POST requests correctly" in {
    val path = "/post"
    stubFor(
      post(urlEqualTo(path)).willReturn(
        aResponse()
          .withBody(responseBody)
          .withHeader(responseHeaderName, responseHeaderValue)
          .withStatus(responseStatusCode)))

    val uri = s"http://localhost:$port$path"
    val bytes = responseBody.toCharArray.map(_.toByte)
    val request = HttpRequestModel(
      uri,
      POST,
      Some(bytes),
      Seq(
        HttpHeaderModel(
          requestHeaderName,
          requestHeaderValue)))

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        postRequestedFor(urlEqualTo(path))
          .withHeader(requestHeaderName, equalTo(requestHeaderValue))
          .withRequestBody(equalTo(responseBody)))

      response.body.string shouldBe responseBody
      response.status shouldBe responseStatusCode
      response.headers should contain(HttpHeaderModel(responseHeaderName, responseHeaderValue))
    }
  }

  it should "make POST requests without body and headers correctly" in {
    val path = "/post"
    stubFor(
      post(urlEqualTo(path)).willReturn(
        aResponse()
          .withBody(responseBody)
          .withHeader(responseHeaderName, responseHeaderValue)
          .withStatus(responseStatusCode)))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      POST,
      None,
      Seq.empty)

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        postRequestedFor(urlEqualTo(path))
          .withRequestBody(equalTo("")))

      response.body.string shouldBe responseBody
      response.status shouldBe responseStatusCode
      response.headers should contain(HttpHeaderModel(responseHeaderName, responseHeaderValue))
    }
  }


}
