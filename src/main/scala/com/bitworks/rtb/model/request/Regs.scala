package com.bitworks.rtb.model.request

/** Any legal, governmental, or industry regulations that apply to the request.
  * The coppa flag signals whether or not the request falls under
  * the United States Federal Trade Commission’s regulations for
  * the United States Children’s Online Privacy Protection Act (“COPPA”).
  *
  * @param coppa Flag indicating if this request is subject to the COPPA
  *              regulations established by the USA FTC, where 0 = no, 1 = yes.
  * @param ext Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Regs(coppa: Int, ext: Option[Any])
