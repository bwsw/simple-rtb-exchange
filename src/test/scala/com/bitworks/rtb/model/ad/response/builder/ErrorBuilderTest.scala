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
  val unknownErrorMsg = "Unknown error"
  val missedHeaderMsg = "Missed header"
  val incorrectHeaderValueMsg = "Incorrect header value"
  val incorrectRequestMsg = "Incorrect request"
  val siteOrAppNotFoundMsg = "Site or app not found"
  val siteOrAppInactive = "Inactive site or app"
  val publisherNotFound = "Publisher not found"
  val iabCategoryNotFound = "IAB category not found"
  val noAdFound = "No ad found"

  val errorMessages = ConfigFactory.parseMap(
    Map(
      "UNKNOWN_ERROR" -> unknownErrorMsg,
      "MISSED_HEADER" -> missedHeaderMsg,
      "INCORRECT_HEADER_VALUE" -> incorrectHeaderValueMsg,
      "INCORRECT_REQUEST" -> incorrectRequestMsg,
      "SITE_OR_APP_NOT_FOUND" -> siteOrAppNotFoundMsg,
      "SITE_OR_APP_INACTIVE" -> siteOrAppInactive,
      "PUBLISHER_NOT_FOUND" -> publisherNotFound,
      "IAB_CATEGORY_NOT_FOUND" -> iabCategoryNotFound,
      "NO_AD_FOUND" -> noAdFound))

  val errors = Table(
    ("code", "message"),
    (UNKNOWN_ERROR, unknownErrorMsg),
    (MISSED_HEADER, missedHeaderMsg),
    (INCORRECT_HEADER_VALUE, incorrectHeaderValueMsg),
    (INCORRECT_REQUEST, incorrectRequestMsg),
    (SITE_OR_APP_NOT_FOUND, siteOrAppNotFoundMsg),
    (SITE_OR_APP_INACTIVE, siteOrAppInactive),
    (PUBLISHER_NOT_FOUND, publisherNotFound),
    (IAB_CATEGORY_NOT_FOUND, iabCategoryNotFound),
    (NO_AD_FOUND, noAdFound))

  "ErrorBuilder" should "create Error correctly" in {
    forAll(errors) { (code: Value, message: String) =>
      val error = ErrorBuilder(code, errorMessages).build
      val expectedError = Error(code.id, message)

      error shouldBe expectedError
    }
  }
}
