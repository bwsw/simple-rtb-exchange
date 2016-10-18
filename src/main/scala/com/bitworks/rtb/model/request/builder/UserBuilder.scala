package com.bitworks.rtb.model.request.builder

import com.bitworks.rtb.model.request.{Data, Geo, User}

/** Builder for User model  */
object UserBuilder {
  protected class UserBuilder{
    private var id: Option[String] = None
    private var buyerid: Option[String] = None
    private var yob: Option[Int] = None
    private var gender: Option[String] = None
    private var keywords: Option[String] = None
    private var customdata: Option[String] = None
    private var geo: Option[Geo] = None
    private var data: Option[Seq[Data]] = None
    private var ext: Option[Any] = None

    def withId(s:String) = { id = Some(s); this }
    def withBuyerid(s:String) = { buyerid = Some(s); this }
    def withYob(i:Int) = { yob = Some(i); this }
    def withGender(s:String) = { gender = Some(s); this }
    def withKeywords(s:String) = { keywords = Some(s); this }
    def withCustomdata(s:String) = { customdata = Some(s); this }
    def withGeo(g:Geo) = { geo = Some(g); this }
    def withData(s:Seq[Data] ) = { data = Some(s); this }
    def withExt(a:Any) = { ext = Some(a); this }

    /** Returns User */
    def build = User(id, buyerid, yob, gender, keywords,  customdata, geo, data, ext)
  }

  /** Returns builder for User */
  def builder = new UserBuilder
}
