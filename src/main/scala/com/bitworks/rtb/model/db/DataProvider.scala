package com.bitworks.rtb.model.db

/**
  * Data provider.
  *
  * @author Egor Ilchenko
  */
case class DataProvider(
    ID: Int,
    name: String,
    segments: Seq[Segment]) extends BaseEntity
