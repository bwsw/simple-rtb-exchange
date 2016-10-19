package com.bitworks.rtb.model.request

import com.bitworks.rtb.model.constant.LocationType

/** Various methods for specifying a geographic location.
  *
  * When subordinate to a Device object, it indicates the location of the device which
  * can also be interpreted as the user’s current location. When subordinate to a
  * User object, it indicates the location of the user’s home base
  * (i.e., not necessarily their current location).
  *
  * The lat/lon  attributes should only be passed if they conform to the accuracy
  * depicted in the type attribute. For example, the centroid of a geographic
  * region such as postal code should not be passed.
  *
  * @param lat Latitude from -90.0 to +90.0, where negative is south.
  * @param lon Longitude from -180.0 to +180.0, where negative is west.
  * @param `type` Source of location data; recommended when passing lat/lon.
  * @param country Country code using ISO-3166-1-alpha-3.
  * @param region Region code using ISO-3166-2; 2-letter state code if USA.
  * @param regionfips104 Region of a country using FIPS 10-4 notation.
  * @param metro Google metro code; similar to but not exactly Nielsen DMAs.
  * @param city City using United Nations Code for Trade & Transport Locations.
  * @param zip Zip or postal code.
  * @param utcoffset Local time as the number +/- of minutes from UTC.
  * @param ext Placeholder for exchange-specific extensions to OpenRTB.
  */
case class Geo(
    lat: Option[Float],
    lon: Option[Float],
    `type`: Option[LocationType.Value],
    country: Option[String],
    region: Option[String],
    regionfips104: Option[String],
    metro: Option[String],
    city: Option[String],
    zip: Option[String],
    utcoffset: Option[Int],
    ext: Option[Any])
