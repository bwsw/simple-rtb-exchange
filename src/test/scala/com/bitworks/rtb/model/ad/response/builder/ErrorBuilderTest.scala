package com.bitworks.rtb.model.ad.response.builder

import com.bitworks.rtb.model.ad.response.Error
import com.bitworks.rtb.model.ad.response.ErrorCode._
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.ad.response.builder.ErrorBuilder ErrorBuilder]].
  *
  * @author Pavel Tomskikh
  */
class ErrorBuilderTest extends FlatSpec with Matchers with TableDrivenPropertyChecks {
  val errors = Table(
    ("code", "message"),
    (UNKNOWN_ERROR, "Unknown error"),
    (MISSING_HEADER, "Header is missed"),
    (INCORRECT_HEADER_VALUE, "Header values is incorrect"),
    (INCORRECT_REQUEST, "Request is incorrect"),
    (SITE_OR_APP_NOT_FOUND, "Site or app not found in DB"),
    (SITE_OR_APP_INACTIVE, "Site or app is inactive"),
    (PUBLISHER_NOT_FOUND, "Publisher app not found in DB"),
    (IAB_CATEGORY_NOT_FOUND, "IAB category app not found in DB"),
    (NO_AD_FOUND, "Ad not found"))

  "ErrorBuilder" should "create Error correctly" in {
    forAll(errors) { (code: Value, message: String) =>
      val error = ErrorBuilder(code).build
      val expectedError = Error(code, message)

      error shouldBe expectedError
    }
  }
}
