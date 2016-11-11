package com.bitworks.rtb.service.factory

import java.time.LocalDate

import com.bitworks.rtb.model.ad
import com.bitworks.rtb.model.ad.request.AdRequest
import com.bitworks.rtb.model.request._
import com.bitworks.rtb.model.request.builder._

/**
  * Factory interface for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @author Egor Ilchenko
  */
trait BidRequestFactory {

  /**
    * Returns [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
    *
    * @param ad [[com.bitworks.rtb.model.ad.request.AdRequest AdRequest]] object
    */
  def create(ad: AdRequest): Option[BidRequest]
}

/**
  * Factory implementation for [[com.bitworks.rtb.model.request.BidRequest BidRequest]].
  *
  * @author Pavel Tomskikh
  */
class BidRequestFactoryImpl extends BidRequestFactory {

  override def create(ad: AdRequest) = ???

  private def create(adImp: ad.request.Imp): Option[Imp] = ???

  private def check(banner: Banner): Boolean = ???

  private def check(video: Video): Boolean = ???

  private def check(native: Native): Boolean = ???

  private def create(adSite: ad.request.Site): Option[Site] = ???

  private def create(adApp: ad.request.App): Option[App] = ???

  private def check(content: Content): Boolean = ???

  private def check(producer: Producer): Boolean = ???

  private def check(device: Device): Boolean = ???

  private def create(adUser: ad.request.User): Option[User] = {
    val builder = UserBuilder()
    adUser.id.foreach(builder.withId)

    val yearDownBound = 1900
    val availGenders = Seq("M", "F", "O")

    if (adUser.yob.forall(between(yearDownBound, nowYear)) &&
      adUser.gender.forall(availGenders.contains) &&
      adUser.geo.forall(check)) {

      adUser.yob.foreach(builder.withYob)
      adUser.gender.foreach(builder.withGender)
      adUser.geo.foreach(builder.withGeo)
      adUser.keywords.foreach(builder.withKeywords)

      Some(builder.build)
    }
    else None
  }

  private def check(geo: Geo): Boolean = {
    def checkRegion(s: String): Boolean = ???
    def checkCountry(s: String): Boolean = ???
    def checkRegionFips104(s: String): Boolean = ???
    def checkMetro(s: String): Boolean = ???
    def checkCity(s: String): Boolean = ???
    def checkZip(s: String): Boolean = ???
    val locationTypes = Range(1, 3)

    geo.lat.forall(between(-90f, 90f)) &&
      geo.lon.forall(between(-180f, 180f)) &&
      geo.`type`.forall(locationTypes.contains) &&
      geo.country.forall(checkCountry) &&
      geo.region.forall(checkRegion) &&
      geo.regionFips104.forall(checkRegionFips104) &&
      geo.metro.forall(checkMetro) &&
      geo.city.forall(checkCity) &&
      geo.zip.forall(checkZip)
  }

  private def check(regs: Regs): Boolean = regs.coppa.forall(isFlag)

  private val nowYear = LocalDate.now.getYear

  private def isFlag(i: Int) = i == 0 || i == 1

  private def between(left: Int, right: Int)(i: Int) = left <= i && i <= right

  private def between(left: Float, right: Float)(i: Float) = left <= i && i <= right
}
