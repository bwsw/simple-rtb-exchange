package com.bitworks.rtb.model.request

/**
  * An ad placement or impression being auctioned. The type of impression is subordinated by the
  * presence of [[com.bitworks.rtb.model.request.Banner Banner]],
  * [[com.bitworks.rtb.model.request.Video Video]] and/or
  * [[com.bitworks.rtb.model.request.Native Native]].
  *
  * @param id                a unique identifier for this impression within the context of the
  *                          bid request
  * @param banner            a [[com.bitworks.rtb.model.request.Banner Banner]] object
  * @param video             a [[com.bitworks.rtb.model.request.Video Video]] object
  * @param native            a [[com.bitworks.rtb.model.request.Native Native]] object
  * @param displayManager    name of ad mediation partner, SDK technology, or player responsible for
  *                          rendering ad
  * @param displayManagerVer version of ad mediation partner, SDK technology, or player responsible
  *                          for rendering ad
  * @param instl             indicator for ad type where 1 = the ad is interstitial or full screen,
  *                          0 = not interstitial
  * @param tagId             identifier for specific ad placement or ad tag that was used to
  *                          initiate the auction
  * @param bidFloor          minimum bid for this impression expressed in CPM
  * @param bidFloorCur       currency specified using ISO-4217 alpha codes
  * @param secure            flag to indicate if the impression requires secure HTTPS URL
  *                          creative assets and markup, where 0 = non-secure, 1 = secure. If
  *                          omitted, the secure state is unknown, but non-secure HTTP support
  *                          can be assumed.
  * @param iframeBuster      array of exchange-specific names of supported iframe busters
  * @param pmp               a [[com.bitworks.rtb.model.request.Pmp Pmp]] object containing any
  *                          private marketplace deals in effect for this impression
  * @param ext               placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Imp(
    id: String,
    banner: Option[Banner],
    video: Option[Video],
    native: Option[Native],
    displayManager: Option[String],
    displayManagerVer: Option[String],
    instl: Int,
    tagId: Option[String],
    bidFloor: BigDecimal,
    bidFloorCur: String,
    secure: Option[Int],
    iframeBuster: Option[Seq[String]],
    pmp: Option[Pmp],
    ext: Option[Any])
