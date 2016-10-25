package com.bitworks.rtb.model.response.native

/**
  * The main container for each asset requested or supported by Exchange
  * on behalf of the rendering client.
  *
  * @param id       unique asset ID
  * @param required set to 1 if asset is required
  * @param title    [[com.bitworks.rtb.model.response.native.Title Title]] object
  * @param img      [[com.bitworks.rtb.model.response.native.Image Image]] object
  * @param video    [[com.bitworks.rtb.model.response.native.Video Video]] object
  * @param data     [[com.bitworks.rtb.model.response.native.Data Data]] object
  *                 for ratings, prices, etc
  * @param link     [[com.bitworks.rtb.model.response.native.Link Link]] object
  * @param ext      placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Asset(
    id: String,
    required: Int,
    title: Option[Title],
    img: Option[Image],
    video: Option[Video],
    data: Option[Data],
    link: Option[Link],
    ext: Option[Any])


