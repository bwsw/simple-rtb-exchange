package com.bitworks.rtb.model.response.native

/**
  * Corresponds to the Data Object in the request, with the value filled in.
  *
  * @param label formatted string name of the data type to be displayed
  * @param value formatted string of data to be displayed
  * @param ext placeholder for bidder-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Data(
  label: Option[String],
  value: String,
  ext: Option[Any])
