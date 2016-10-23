package com.bitworks.rtb.model.request.native

/**
  * Information about a main container for each asset requested or supported by Exchange on
  * behalf of the rendering client.
  *
  * @param id       unique asset id
  * @param required indicates whether asset is required where 1 = required
  * @param title    [[com.bitworks.rtb.model.request.native.Title Title]] object
  * @param img      [[com.bitworks.rtb.model.request.native.Image Image]] object
  * @param video    [[com.bitworks.rtb.model.request.native.Video Video]] object
  * @param data     [[com.bitworks.rtb.model.request.native.Data Data]] object
  * @param ext      placeholder for exchange-specific extensions to OpenRTB
  * @author Pavel Tomskikh
  */
case class Asset(
    id: Int,
    required: Int,
    title: Option[Title],
    img: Option[Image],
    video: Option[Video],
    data: Option[Data],
    ext: Option[Any])
