package com.bitworks.rtb.model.request

/** Additional data about the user.
  *
  * The data and segment objects together allow additional data about
  * the user to be specified. This data may be from multiple sources whether
  * from the exchange itself or third party providers as specified by
  * the id field. A bid request can mix data objects from multiple providers.
  * The specific data providers in use should be published by the exchange a
  * priori to its bidders.
  *
  * Created on: 10/19/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  * All Rights Reserved (c) 2016 Bitworks Software, Ltd.  *
  *
  * @param id Exchange-specific ID for the data provider.
  * @param name Exchange-specific name for the data provider
  * @param segment Segment objects that contain the actual data values
  * @param ext Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Data(
    id: Option[String],
    name: Option[String],
    segment: Option[Seq[Segment]],
    ext: Option[Any])
