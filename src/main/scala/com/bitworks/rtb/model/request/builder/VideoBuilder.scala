package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Banner, Video}

/** Builder for Video model
  *
  * Created on: 10/19/2016
  *
  * @author Egor Ilchenko
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  */
class VideoBuilder private(mimes: Seq[String]) {
  private var minduration: Option[Int] = None
  private var maxduration: Option[Int] = None
  private var protocol: Option[Int] = None
  private var protocols: Option[Seq[Int]] = None
  private var w: Option[Int] = None
  private var h: Option[Int] = None
  private var startdelay: Option[Int] = None
  private var linearity: Option[Int] = None
  private var sequence: Option[Int] = None
  private var battr: Option[Seq[Int]] = None
  private var maxextended: Option[Int] = None
  private var minbitrate: Option[Int] = None
  private var maxbitrate: Option[Int] = None
  private var boxingallowed: Int = 1
  private var playbackmethod: Option[Seq[Int]] = None
  private var delivery: Option[Seq[Int]] = None
  private var pos: Option[Int] = None
  private var companionad: Option[Seq[Banner]] = None
  private var api: Option[Seq[Int]] = None
  private var companiontype: Option[Seq[Int]] = None
  private var ext: Option[Any] = None

  def withMinduration(i: Int) = {
    minduration = Some(i)
    this
  }

  def withMaxduration(i: Int) = {
    maxduration = Some(i)
    this
  }

  def withProtocol(i: Int) = {
    protocol = Some(i)
    this
  }

  def withProtocols(s: Seq[Int]) = {
    protocols = Some(s)
    this
  }

  def withW(i: Int) = {
    w = Some(i)
    this
  }

  def withH(i: Int) = {
    h = Some(i)
    this
  }

  def withStartdelay(i: Int) = {
    startdelay = Some(i)
    this
  }

  def withLinearity(i: Int) = {
    linearity = Some(i)
    this
  }

  def withSequence(i: Int) = {
    sequence = Some(i)
    this
  }

  def withBattr(s: Seq[Int]) = {
    battr = Some(s)
    this
  }

  def withMaxextended(i: Int) = {
    maxextended = Some(i)
    this
  }

  def withMinbitrate(i: Int) = {
    minbitrate = Some(i)
    this
  }

  def withMaxbitrate(i: Int) = {
    maxbitrate = Some(i)
    this
  }

  def withBoxingallowed(i: Int) = {
    boxingallowed = i
    this
  }

  def withPlaybackmethod(s: Seq[Int]) = {
    playbackmethod = Some(s)
    this
  }

  def withDelivery(s: Seq[Int]) = {
    delivery = Some(s)
    this
  }

  def withPos(i: Int) = {
    pos = Some(i)
    this
  }

  def withCompanionad(s: Seq[Banner]) = {
    companionad = Some(s)
    this
  }

  def withApi(s: Seq[Int]) = {
    api = Some(s)
    this
  }

  def withCompaniontype(s: Seq[Int]) = {
    companiontype = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Video */
  def build = Video(mimes, minduration, maxduration, protocol, protocols, w, h, startdelay,
    linearity, sequence, battr, maxextended, minbitrate, maxbitrate, boxingallowed,
    playbackmethod, delivery, pos, companionad, api, companiontype, ext)
}

/** Builder for Video model */
object VideoBuilder {
  def apply(mimes: Seq[String]): VideoBuilder = new VideoBuilder(mimes)
}
