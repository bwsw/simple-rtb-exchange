package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.Error
import com.bitworks.rtb.model.ad.response.ErrorCode._
import com.typesafe.config.ConfigFactory
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConversions.mapAsJavaMap

/**
  * Test for [[com.bitworks.rtb.model.ad.response.builder.ErrorBuilder ErrorBuilder]].
  *
  * @author Pavel Tomskikh
  */
class ErrorBuilderTest extends FlatSpec with Matchers with TableDrivenPropertyChecks {
  val errors = Table(
    ("code", "message"),
    (UNKNOWN_ERROR, "Unknown error"),
    (MISSED_HEADER, "Missed header"),
    (INCORRECT_HEADER_VALUE, "Incorrect header value"),
    (INCORRECT_REQUEST, "Incorrect request"),
    (SITE_OR_APP_NOT_FOUND, "Site or app not found"),
    (SITE_OR_APP_INACTIVE, "Inactive site or app"),
    (PUBLISHER_NOT_FOUND, "Publisher not found"),
    (IAB_CATEGORY_NOT_FOUND, "IAB category not found"),
    (NO_AD_FOUND, "No ad found"))

  "ErrorBuilder" should "create Error correctly" in {
    forAll(errors) { (code: Value, message: String) =>
      val errorMessages = ConfigFactory.parseMap(Map(code.toString -> message))
      val error = ErrorBuilder(code, errorMessages).build
      val expectedError = Error(code.id, message)

      error shouldBe expectedError
    }
  }
}
