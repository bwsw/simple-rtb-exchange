package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.request.native.builder.AssetBuilder AssetBuilder]].
  *
  * @author Pavel Tomskikh
  */
class AssetBuilderTest extends FlatSpec with Matchers {

  "AssetBuilder" should "build Asset with default values correctly" in {
    val asset = Asset(55, AssetBuilder.Required, None, None, None, None, None)
    val builtAsset = AssetBuilder(asset.id).build

    builtAsset shouldBe asset
  }

  it should "build Asset correctly" in {
    val title = Title(123, None)
    val img = Image(None, None, None, None, None, None, None)
    val video = Video(Seq("image/jpg"), 10, 30, Seq(1, 4), None)
    val data = Data(3, None, None)
    val asset = Asset(
      123,
      1,
      Some(title),
      Some(img),
      Some(video),
      Some(data),
      Some("ext"))

    var builder = AssetBuilder(asset.id).withRequired(asset.required)
    asset.title.foreach(title => builder = builder.withTitle(title))
    asset.img.foreach(img => builder = builder.withImg(img))
    asset.video.foreach(video => builder = builder.withVideo(video))
    asset.data.foreach(data => builder = builder.withData(data))
    asset.ext.foreach(ext => builder = builder.withExt(ext))

    val builtAsset = builder.build

    builtAsset shouldBe asset
  }
}
