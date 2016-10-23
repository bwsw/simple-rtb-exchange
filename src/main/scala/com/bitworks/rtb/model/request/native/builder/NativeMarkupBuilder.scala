package com.bitworks.rtb.model.request.native.builder

import com.bitworks.rtb.model.request.native.{Asset, NativeMarkup}

/**
  * Builder for [[com.bitworks.rtb.model.request.native.NativeMarkup NativeMarkup]].
  *
  * @param assets value of assets in
  *               [[com.bitworks.rtb.model.request.native.NativeMarkup NativeMarkup]] object
  * @author Pavel Tomskikh
  */
class NativeMarkupBuilder private(assets: Seq[Asset]) {
  private var ver: String = NativeMarkupBuilder.Ver
  private var layout: Option[Int] = None
  private var adUnit: Option[Int] = None
  private var plcmtCnt: Int = NativeMarkupBuilder.plcmtCnt
  private var seq: Int = NativeMarkupBuilder.Seq
  private var ext: Option[Any] = None

  def withVer(s: String) = {
    ver = s
    this
  }

  def withLayout(i: Int) = {
    layout = Some(i)
    this
  }

  def withAdUnit(i: Int) = {
    adUnit = Some(i)
    this
  }

  def withPlcmtCnt(i: Int) = {
    plcmtCnt = i
    this
  }

  def withSeq(i: Int) = {
    seq = i
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  def build = NativeMarkup(ver, layout, adUnit, plcmtCnt, seq, assets, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.native.NativeMarkup NativeMarkup]].
  *
  * @author Pavel Tomskikh
  */
object NativeMarkupBuilder {
  val Ver = "1"
  val plcmtCnt = 1
  val Seq = 0

  def apply(assets: Seq[Asset]) = new NativeMarkupBuilder(assets)
}
