package com.bitworks.rtb.model.response.native

/**
  * The main container for each asset nrequested or supported by Exchange
  * on behalf of the rendering client.
  *
  * @param id       unique asset ID
  * @param required set to 1 if asset is required
  * @param title    title object
  * @param img      image object
  * @param video    video object
  * @param data     data object for ratings, prices, etc
  * @param link     link object
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


