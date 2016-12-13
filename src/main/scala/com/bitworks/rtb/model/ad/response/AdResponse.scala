package com.bitworks.rtb.model.ad.response

import com.bitworks.rtb.model.http.ContentTypeModel

/**
  * The top-level ad response object.
  *
  * @param id    ID of the ad response
  * @param imp   array of [[com.bitworks.rtb.model.ad.response.Imp Imp]] objects representing
  *              impressions
  * @param error details via [[com.bitworks.rtb.model.ad.response.Error Error]] object about error
  * @param ct    request content type
  * @author Pavel Tomskikh
  */
case class AdResponse(
    id: Option[String],
    imp: Option[Seq[Imp]],
    error: Option[Error],
    ct: ContentTypeModel)
