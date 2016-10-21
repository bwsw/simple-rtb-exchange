package com.bitworks.rtb.model.request.native

/**
  * Information about a main container for each asset requested or supported by Exchange on
  * behalf of the rendering client.
  *
  * @param id       unique assets id
  * @param required indicates if asset is required
  * @param title    describes a title
  * @param img      describes a image
  * @param video    describes a video
  * @param data     describes a data
  * @param ext      placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  *
  */
case class Asset(
    id: Int,
    required: Option[Int],
    title: Option[Title],
    img: Option[Image],
    video: Option[Video],
    data: Option[Data],
    ext: Option[Any])
