package com.bitworks.rtb.model.request

/** Ad placement or impression being auctioned.
  *
  * A single bid request can include multiple Imp objects, a use case for which might be an
  * exchange that supports selling all ad positions on a given page. Each Imp object has a
  * required ID so that bids can reference them individually.
  *
  * The presence of Banner (Section 3.2.3), Video (Section 3.2.4), and/or Native (Section 3.2.5)
  * objects subordinate to the Imp object indicates the type of impression being offered. The
  * publisher can choose one such type which is the typical case or mix them at their discretion.
  * However, any given bid for the impression must conform to one of the offered types.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * @param id                A unique identifier for this impression within the context of the
  *                          bid request
  *                          (typically, starts with 1 and increments.
  * @param banner            A Banner object (Section 3.2.3); required if this impression is
  *                          offered as a
  *                          banner ad opportunity.
  * @param video             A Video object (Section 3.2.4); required if this impression is
  *                          offered as a
  *                          video ad opportunity.
  * @param native            A Native object (Section 3.2.5); required if this impression is
  *                          offered as a
  *                          native ad opportunity
  * @param displaymanager    Name of ad mediation partner, SDK technology, or player responsible for
  *                          rendering ad (typically video or mobile). Used by some ad servers to
  *                          customize ad code by partner. Recommended for video and/or apps.
  * @param displaymanagerver Version of ad mediation partner, SDK technology, or player
  *                          responsible for rendering ad (typically video or mobile). Used by
  *                          some ad servers to customize ad code by partner. Recommended for
  *                          video and/or apps.
  * @param instl             1 = the ad is interstitial or full screen, 0 = not interstitial.
  * @param tagid             Identifier for specific ad placement or ad tag that was used to
  *                          initiate the
  *                          auction. This can be useful for debugging of any issues, or for
  *                          optimization by the buyer
  * @param bidfloor          Minimum bid for this impression expressed in CPM.
  * @param bidfloorcur       Currency specified using ISO-4217 alpha codes. This may be different
  *                          from
  *                          bid currency returned by bidder if this is allowed by the exchange.
  * @param secure            Flag to indicate if the impression requires secure HTTPS URL
  *                          creative assets
  *                          and markup, where 0 = non-secure, 1 = secure. If omitted, the secure
  *                          state is
  *                          unknown, but non-secure HTTP support can be assumed.
  * @param iframebuster      Array of exchange-specific names of supported iframe busters.
  * @param pmp               A Pmp object (Section 3.2.17) containing any private marketplace
  *                          deals in effect
  *                          for this impression
  * @param ext               Placeholder for exchange-specific extensions to OpenRTB.
  */

case class Imp(
    id: String,
    banner: Option[Banner],
    video: Option[Video],
    native: Option[Native],
    displaymanager: Option[String],
    displaymanagerver: Option[String],
    instl: Int,
    tagid: Option[String],
    bidfloor: BigDecimal,
    bidfloorcur: String,
    secure: Option[Int],
    iframebuster: Option[Seq[String]],
    pmp: Option[Pmp],
    ext: Option[Any])
