package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.Pmp
import org.scalatest.{FlatSpec, Matchers}

/** Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class PmpBuilderTest extends FlatSpec with Matchers{

  "PmpBuilder" should "build Pmp correctly" in {
    val pmp = Pmp(Some(1), Some(Seq.empty), Some("string"))

    val buildedPmp = PmpBuilder()
        .withPrivateAuction(1)
        .withDeals(Seq.empty)
        .withExt("string")
        .build

    buildedPmp shouldBe pmp
  }

}