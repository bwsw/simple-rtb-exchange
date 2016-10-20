package com.bitworks.rtb.model.request

/**
  * Any legal, governmental, or industry regulations that apply to the request.
  *
  * @param coppa flag indicating if this request is subject to the COPPA
  *              regulations established by the USA FTC, where 0 = no, 1 = yes
  * @param ext   placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Regs(coppa: Int, ext: Option[Any])
