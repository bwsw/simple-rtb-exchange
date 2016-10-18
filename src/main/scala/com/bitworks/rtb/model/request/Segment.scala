package com.bitworks.rtb.model.request

/** Specific units of data about the user.
  *
  * The parent Data object is a collection of such values from
  * a given data provider. The specific segment names and value options
  * must be published by the exchange a priori to its bidders.
  *
  * @param id ID of the data segment specific to the data provider.
  * @param name Name of the data segment specific to the data provider.
  * @param value String representation of the data segment value.
  * @param ext Placeholder for exchange-specific extensions to OpenRTB
  */
case class Segment(
                    id: Option[String], name: Option[String],
                    value: Option[String], ext: Option[Any]
                  )
