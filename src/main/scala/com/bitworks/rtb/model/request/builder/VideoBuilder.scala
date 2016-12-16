package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Banner, Video}

/**
  * Builder for [[com.bitworks.rtb.model.request.Video Video]].
  *
  * @param mimes value of mimes in [[com.bitworks.rtb.model.request.Video Video]] object
  * @author Egor Ilchenko
  */
class VideoBuilder private(mimes: Seq[String]) {
  private var minDuration: Option[Int] = None
  private var maxDuration: Option[Int] = None
  private var protocol: Option[Int] = None
  private var protocols: Option[Seq[Int]] = None
  private var w: Option[Int] = None
  private var h: Option[Int] = None
  private var startDelay: Option[Int] = None
  private var linearity: Option[Int] = None
  private var sequence: Option[Int] = None
  private var battr: Option[Seq[Int]] = None
  private var maxExtended: Option[Int] = None
  private var minBitrate: Option[Int] = None
  private var maxBitrate: Option[Int] = None
  private var boxingAllowed: Int = VideoBuilder.BoxingAllowed
  private var playbackMethod: Option[Seq[Int]] = None
  private var delivery: Option[Seq[Int]] = None
  private var pos: Option[Int] = None
  private var companionAd: Option[Seq[Banner]] = None
  private var api: Option[Seq[Int]] = None
  private var companionType: Option[Seq[Int]] = None
  private var ext: Option[Any] = None

  def withMinDuration(i: Int) = {
    minDuration = Some(i)
    this
  }

  def withMaxDuration(i: Int) = {
    maxDuration = Some(i)
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

  def withStartDelay(i: Int) = {
    startDelay = Some(i)
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

  def withMaxExtended(i: Int) = {
    maxExtended = Some(i)
    this
  }

  def withMinBitrate(i: Int) = {
    minBitrate = Some(i)
    this
  }

  def withMaxBitrate(i: Int) = {
    maxBitrate = Some(i)
    this
  }

  def withBoxingAllowed(i: Int) = {
    boxingAllowed = i
    this
  }

  def withPlaybackMethod(s: Seq[Int]) = {
    playbackMethod = Some(s)
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

  def withCompanionAd(s: Seq[Banner]) = {
    companionAd = Some(s)
    this
  }

  def withApi(s: Seq[Int]) = {
    api = Some(s)
    this
  }

  def withCompanionType(s: Seq[Int]) = {
    companionType = Some(s)
    this
  }

  def withExt(a: Any) = {
    ext = Some(a)
    this
  }

  /** Returns Video */
  def build = Video(mimes, minDuration, maxDuration, protocol, protocols, w, h, startDelay,
    linearity, sequence, battr, maxExtended, minBitrate, maxBitrate, boxingAllowed,
    playbackMethod, delivery, pos, companionAd, api, companionType, ext)
}

/**
  * Builder for [[com.bitworks.rtb.model.request.Video Video]].
  *
  * @author Egor Ilchenko
  */
object VideoBuilder {
  val BoxingAllowed = 1
  
  def apply(mimes: Seq[String]): VideoBuilder = new VideoBuilder(mimes)
}
