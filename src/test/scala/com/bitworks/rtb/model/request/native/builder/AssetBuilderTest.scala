package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.AssetBuilder]]
  *
  * @author Pavel Tomskikh
  *
  */
class AssetBuilderTest extends FlatSpec with Matchers {

  "AssetBuilder" should "build Asset with default parameters correctly" in {
    val asset = Asset(55, Some(0), None, None, None, None, None)
    val buildedAsset = AssetBuilder(55).build

    buildedAsset shouldBe asset
  }

  it should "build Asset with optional parameters correctly" in {
    val title = Title(123, None)
    val img = Image(None, None, None, None, None, None, None)
    val video = Video(None, None, None, None, None)
    val data = Data(3, None, None)
    val asset = Asset(
      123,
      Some(1),
      Some(title),
      Some(img),
      Some(video),
      Some(data),
      Some("ext"))
    val buildedAsset = AssetBuilder(123)
        .withRequired(1)
        .withTitle(title)
        .withImg(img)
        .withVideo(video)
        .withData(data)
        .withExt("ext")
        .build

    buildedAsset shouldBe asset
  }
}
