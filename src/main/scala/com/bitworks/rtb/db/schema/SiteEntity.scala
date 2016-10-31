package com.bitworks.rtb.db.schema

import com.bitworks.rtb.model.db.BaseEntity

/**
  * Database site entity.
  *
  * @author Egor Ilchenko
  */
case class SiteEntity(
    ID: Int,
    tsVersion: Long,
    deleted: Int,
    publisherId: Int,
    name: String,
    domain: String,
    privacyPolicy: Int,
    keyword: String,
    isApp: Int,
    appBundle: String,
    appStoreUrl: String,
    appVer: String) extends BaseEntity with EntityMetaInfo
