package com.bitworks.rtb.writer.response

import com.bitworks.rtb.model.ad.response.AdResponse

/**
  * Writer for [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]].
  *
  * @author Egor Ilchenko
  */
trait AdResponseWriter {

  /**
    * Returns generated string.
    *
    * @param response [[com.bitworks.rtb.model.ad.response.AdResponse AdResponse]] object
    */
  def write(response: AdResponse) : String
}
