package com.bitworks.rtb.model.request.native_adv

/**
  *
  * Created on: 10/18/2016
  *
  * @author Pavel Tomskih
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  *
  * The main container object for each asset requested or supported by Exchange
  * on behalf of the rendering client.
  *
  * @param id       unique assets id
  * @param required indicates if asset is required
  * @param title    title object for title assets
  * @param img      image object for image assets
  * @param video    video object for video assets
  * @param data     data object for ratings, prices etc.
  * @param ext      a placeholder for exchange-specific extensions to OpenRTB
  */
case class Asset(
    id: Int,
    required: Boolean,
    title: Option[Title],
    img: Option[Image],
    video: Option[Video],
    data: Option[Data],
    ext: Option[Any])
