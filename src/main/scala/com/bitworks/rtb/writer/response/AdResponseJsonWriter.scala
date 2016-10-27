package com.bitworks.rtb.writer.response

import com.bitworks.rtb.model.ad.response.{AdResponse, Error, Imp}
import com.bitworks.rtb.writer.JsonWriteHelper

/**
  * JSON writer for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @author Egor Ilchenko
  */
class AdResponseJsonWriter extends AdResponseWriter with JsonWriteHelper {

  override def write(response: AdResponse): String = {
    val rootNode = getAdResponseNode(response)
    mapper.writeValueAsString(rootNode)
  }

  private def getAdResponseNode(response: AdResponse) = createObject
    .put("id", response.id)
    .putOption("error", response.error, getErrorNode)
    .putObjectArray("imp", response.imp, getImpNode)

  private def getErrorNode(error: Error) = createObject
    .put("code", error.code)
    .put("message", error.message)

  private def getImpNode(imp: Imp) = createObject
    .put("id", imp.id)
    .put("adm", imp.adm)
    .put("type", imp.`type`)

}
