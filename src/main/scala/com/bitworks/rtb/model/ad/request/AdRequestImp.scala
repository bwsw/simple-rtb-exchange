package com.bitworks.rtb.model.ad.request

import com.bitworks.rtb.model.request.{Banner, Native, Video}

/**
  * Container for the description of a specific impression.
  *
  * @param id     unique identifier for this impression within the context of the bid request
  * @param banner [[com.bitworks.rtb.model.request.Banner Banner]] object
  * @param video  [[com.bitworks.rtb.model.request.Video Video]] object
  * @param native [[com.bitworks.rtb.model.request.Native Native]] object
  * @author Egor Ilchenko
  */
case class AdRequestImp(
    id: String,
    banner: Option[Banner],
    video: Option[Video],
    native: Option[Native])
