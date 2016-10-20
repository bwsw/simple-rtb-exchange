package com.bitworks.rtb.model.request.native_adv.builder

import com.bitworks.rtb.model.request.native_adv.{Asset, NativeMarkup}

/**
  *
  * Created on: 10/19/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * Builder for [[com.bitworks.rtb.model.request.native_adv.NativeMarkup]].
  * @param assets is required for [[com.bitworks.rtb.model.request.native_adv.NativeMarkup]]
  */
class NativeMarkupBuilder private (assets: Seq[Asset]) {
  private var ver: Option[String] = Some("1")
  private var layout: Option[Int] = None
  private var adUnit: Option[Int] = None
  private var plcmtCnt: Option[Int] = Some(1)
  private var seq: Option[Int] = Some(0)
  private var ext: Option[Any] = None

  def withVer(s: String) = { ver = Some(s); this }
  def withLayout(i: Int) = { layout = Some(i); this }
  def withAdUnit(i: Int) = { adUnit = Some(i); this }
  def withPlcmtCnt(i: Int) = { plcmtCnt = Some(i); this }
  def withSeq(i: Int) = { seq = Some(i); this }
  def withExt(a: Any) = { ext = Some(a); this }

  def build = NativeMarkup(ver, layout, adUnit, plcmtCnt, seq, assets, ext)
}

object NativeMarkupBuilder {
  def apply(assets: Seq[Asset]) = new NativeMarkupBuilder(assets)
}
