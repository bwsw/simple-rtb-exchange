package com.bitworks.rtb.service

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.bitworks.rtb.model.http._
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import org.easymock.EasyMock
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.concurrent.duration.FiniteDuration

/**
  * Test for [[com.bitworks.rtb.service.AkkaHttpRequestMaker AkkaHttpRequestMaker]].
  *
  * @author Egor Ilchenko
  */
class AkkaHttpRequestMakerTest extends FlatSpec with BeforeAndAfterAll
  with ScalaFutures with EasyMockSugar with Matchers {

  val port = 6423
  val server = new WireMockServer(wireMockConfig().port(port))

  override def beforeAll = {
    server.start()
    WireMock.configureFor(port)
  }

  override def afterAll = {
    server.shutdown()
  }

  implicit val system = ActorSystem("test")
  implicit val materializer = ActorMaterializer()

  val configuration = mock[Configuration]
  expecting {
    call(configuration.toStrictTimeout).andReturn(FiniteDuration(1, "s")).anyTimes()
  }
  EasyMock.replay(configuration)

  val maker = new AkkaHttpRequestMaker()(system, materializer, configuration)

  private val responseBody = "somestr"
  private val contentTypeHeader = "Content-Type"
  private val jsonContentTypeValue = "application/json"
  private val responseStatusCode = 201
  private val customHeaderName = "customheader"
  private val customHeaderValue = "customheadervalue"

  "Akka http request maker" should "make GET requests correctly" in {
    val path = "/get"
    stubFor(
      get(urlEqualTo(path)).willReturn(
        aResponse()
          .withBody(responseBody)
          .withStatus(responseStatusCode)
          .withHeader(customHeaderName, customHeaderValue)))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      GET,
      None,
      headers = Seq(HttpHeaderModel(customHeaderName, customHeaderValue)))

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        getRequestedFor(urlEqualTo(path))
          .withHeader(customHeaderName, equalTo(customHeaderValue)))

      val bodyAsString = new String(response.body)
      bodyAsString shouldBe responseBody
      response.status shouldBe responseStatusCode
      response.contentType shouldBe Unknown
      response.headers should contain(HttpHeaderModel(customHeaderName, customHeaderValue))
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
          .withHeader(contentTypeHeader, jsonContentTypeValue)
          .withHeader(customHeaderName, customHeaderValue)
          .withStatus(responseStatusCode)))

    val uri = s"http://localhost:$port$path"
    val bytes = responseBody.toCharArray.map(_.toByte)
    val request = HttpRequestModel(
      uri,
      POST,
      Some(bytes),
      Json,
      headers = Seq(HttpHeaderModel(customHeaderName, customHeaderValue)))

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        postRequestedFor(urlEqualTo(path))
          .withHeader(contentTypeHeader, equalTo(jsonContentTypeValue))
          .withHeader(customHeaderName, equalTo(customHeaderValue))
          .withRequestBody(equalTo(responseBody)))

      val bodyAsString = new String(response.body)
      bodyAsString shouldBe responseBody
      response.status shouldBe responseStatusCode
      response.contentType shouldBe Json
      response.headers should contain(HttpHeaderModel(customHeaderName, customHeaderValue))
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

  it should "make POST requests with avro content type correctly" in {
    val path = "/post"
    val avroContentType = "avro/binary"
    stubFor(
      post(urlEqualTo(path)).willReturn(
        aResponse()
          .withHeader("Content-Type", avroContentType)))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      POST,
      None,
      Avro)

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        postRequestedFor(urlEqualTo(path))
          .withHeader("Content-Type", equalTo(avroContentType))
      )

      response.contentType shouldBe Avro
    }
  }

  it should "make POST requests with protobuf content type correctly" in {
    val path = "/post"
    val protobufContentType = "application/x-protobuf"
    stubFor(
      post(urlEqualTo(path)).willReturn(
        aResponse()
          .withHeader(contentTypeHeader, protobufContentType)))

    val uri = s"http://localhost:$port$path"
    val request = HttpRequestModel(
      uri,
      POST,
      None,
      Protobuf)

    val fResponse = maker.make(request)
    whenReady(fResponse, timeout(Span(5, Seconds))) { response =>
      verify(
        postRequestedFor(urlEqualTo(path))
          .withHeader(contentTypeHeader, equalTo(protobufContentType))
      )

      response.contentType shouldBe Protobuf
    }
  }
}
