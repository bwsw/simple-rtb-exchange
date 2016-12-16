package com.bitworks.rtb.model.ad.request

import com.bitworks.rtb.model.http.ContentTypeModel
import com.bitworks.rtb.model.request.{Device, Regs}

/**
  * Ad request.
  *
  * @param id     ID of the ad request
  * @param imp    array of [[com.bitworks.rtb.model.ad.request.Imp Imp]] objects
  * @param site   details via [[com.bitworks.rtb.model.ad.request.Site Site]] object
  * @param app    details via [[com.bitworks.rtb.model.ad.request.App App]] object
  * @param device details via [[com.bitworks.rtb.model.request.Device Device]] object
  * @param user   details via [[com.bitworks.rtb.model.ad.request.User User]] object
  * @param test   indicator of test mode in which auctions are not billable, where 0 = live mode,
  *               1 = test mode
  * @param tmax   maximum time in milliseconds to submit a bid to avoid timeout
  * @param regs   a [[com.bitworks.rtb.model.request.Regs Regs]] object that specifies any
  *               industry, legal or governmental regulations in force for this request
  * @param ct     request content type
  * @author Egor Ilchenko
  */
case class AdRequest(
    id: String,
    imp: Seq[Imp],
    site: Option[Site],
    app: Option[App],
    device: Option[Device],
    user: Option[User],
    test: Int,
    tmax: Option[Int],
    regs: Option[Regs],
    ct: ContentTypeModel)
