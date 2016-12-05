package com.bitworks.rtb.service.writer

import com.bitworks.rtb.model.ad.response.{AdResponse, Error, Imp}

/**
  * JSON writer for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @author Egor Ilchenko
  */
class AdResponseJsonWriter extends AdResponseWriter with JsonWriteHelper {

  override def write(response: AdResponse): Array[Byte] = {
    val rootNode = getAdResponseNode(response)
    mapper.writeValueAsBytes(rootNode)
  }

  private def getAdResponseNode(response: AdResponse) = createObject
    .putOptionString("id", response.id)
    .putOption("error", response.error, getErrorNode)
    .putOptionArray("imp", response.imp, getImpNode)

  private def getErrorNode(error: Error) = createObject
    .put("code", error.code)
    .put("message", error.message)

  private def getImpNode(imp: Imp) = createObject
    .put("id", imp.id)
    .put("adm", imp.adm)
    .put("type", imp.`type`)

}
