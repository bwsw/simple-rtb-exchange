package com.bitworks.rtb.model.response.builder

import com.bitworks.rtb.model.response.Bid

/**
  * Builder for [[com.bitworks.rtb.model.response.Bid Bid]].
  *
  * @author Egor Ilchenko
  */
class BidBuilder private(id: String, impId: String, price: BigDecimal) {
  private var adId: Option[String] = None
  private var nurl: Option[String] = None
  private var adm: Option[String] = None
  private var adomain: Option[Seq[String]] = None
  private var bundle: Option[String] = None
  private var iurl: Option[String] = None
  private var cid: Option[String] = None
  private var crid: Option[String] = None
  private var cat: Option[Seq[String]] = None
  private var attr: Option[Set[Int]] = None
  private var dealId: Option[String] = None
  private var h: Option[Int] = None
  private var w: Option[Int] = None
  private var ext: Option[Any] = None

  def withAdId(s: String) = {
    adId = Some(s)
    this
  }

  def withNurl(s: String) = {
    nurl = Some(s)
    this
  }

  def withAdm(s: String) = {
    adm = Some(s)
    this
  }

  def withAdomain(s: Seq[String]) = {
    adomain = Some(s)
    this
  }

  def withBundle(s: String) = {
    bundle = Some(s)
    this
  }

  def withIurl(s: String) = {
    iurl = Some(s)
    this
  }

  def withCid(s: String) = {
    cid = Some(s)
    this
  }

  def withCrid(s: String) = {
    crid = Some(s)
    this
  }

  def withCat(s: Seq[String]) = {
    cat = Some(s)
    this
  }

  def withAttr(s: Set[Int]) = {
    attr = Some(s)
    this
  }

  def withDealId(s: String) = {
    dealId = Some(s)
    this
  }

  def withH(i: Int) = {
    h = Some(i)
    this
  }

  def withW(i: Int) = {
    w = Some(i)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Bid */
  def build = Bid(id, impId, price, adId, nurl, adm, adomain, bundle, iurl, cid, crid, cat, attr,
    dealId, h, w, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.response.Bid Bid]].
  *
  * @author Egor Ilchenko
  */
object BidBuilder {
  def apply(id: String, impid: String, price: BigDecimal): BidBuilder = new BidBuilder(id, impid,
    price)
}
