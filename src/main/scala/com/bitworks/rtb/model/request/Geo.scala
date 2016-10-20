package com.bitworks.rtb.model.request

/**
  * A geographic location: the location of the [[com.bitworks.rtb.model.request.Device Device]] or
  * the location of the user's home base [[com.bitworks.rtb.model.request.User User]].
  *
  * @param lat           latitude from -90.0 to +90.0, where negative is south
  * @param lon           longitude from -180.0 to +180.0, where negative is west
  * @param `type`        source of location data; recommended when passing lat/lon
  * @param country       country code using ISO-3166-1-alpha-3
  * @param region        region code using ISO-3166-2; 2-letter state code if USA
  * @param regionFips104 region of a country using FIPS 10-4 notation
  * @param metro         Google metro code; similar to but not exactly Nielsen DMAs
  * @param city          city using United Nations Code for Trade & Transport Locations
  * @param zip           zip or postal code
  * @param utcOffset     local time as the number +/- of minutes from UTC
  * @param ext           placeholder for exchange-specific extensions to OpenRTB
  * @author Egor Ilchenko
  */
case class Geo(
    lat: Option[Float],
    lon: Option[Float],
    `type`: Option[Int],
    country: Option[String],
    region: Option[String],
    regionFips104: Option[String],
    metro: Option[String],
    city: Option[String],
    zip: Option[String],
    utcOffset: Option[Int],
    ext: Option[Any])
