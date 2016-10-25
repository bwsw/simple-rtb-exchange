package com.bitworks.rtb.model.response.native.builder

import com.bitworks.rtb.model.response.native.Asset
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.model.response.native.builder.AssetBuilder AssetBuilder]].
  *
  * @author Egor Ilchenko
  */
class AssetBuilderTest extends FlatSpec with Matchers {

  "AssetBuilder" should "build Asset correctly" in {
    val asset = Asset(
      "id",
      2,
      Some(TitleBuilder("123").build),
      Some(ImageBuilder("url").build),
      Some(VideoBuilder("vasttag").build),
      Some(DataBuilder("value").build),
      Some(LinkBuilder("url").build),
      Some("string"))

    val builder = AssetBuilder(asset.id)
      .withRequired(asset.required)
    asset.title.foreach(title => builder.withTitle(title))
    asset.img.foreach(img => builder.withImg(img))
    asset.video.foreach(video => builder.withVideo(video))
    asset.data.foreach(data => builder.withData(data))
    asset.link.foreach(link => builder.withLink(link))
    asset.ext.foreach(ext => builder.withExt(ext))

    val builtAsset = builder.build

    builtAsset shouldBe asset
  }

  it should "build Asset with default values correctly" in {
    val asset = Asset(
      "id",
      AssetBuilder.Required,
      None,
      None,
      None,
      None,
      None,
      None)

    val builtAsset = AssetBuilder(asset.id).build

    builtAsset shouldBe asset
  }

}
