package com.bitworks.rtb.service.dao.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database site entity.
  *
  * @author Egor Ilchenko
  */
case class SiteEntity(
    id: Int,
    tsversion: Long,
    deleted: Boolean,
    publisherId: Int,
    name: String,
    `type`: Int,
    status: Int,
    privacyPolicy: Int,
    test: Boolean,
    domain: Option[String],
    keyword: Option[String],
    appBundle: Option[String],
    appStoreUrl: Option[String],
    appVer: Option[String]) extends BaseEntity with EntityMetaInfo
