package com.bitworks.rtb.service

import akka.actor.ActorSystem
import com.bitworks.rtb.model.http.HttpRequestModel

import scala.concurrent.Future

/**
  *
  *
  * @author Egor Ilchenko
  */
trait WinNoticeRequestMaker {
  def sendWinNotice(nurl: String): Unit

  def getAdMarkup(nurl: String): Future[String]
}

class WinNoticeRequestMakerImpl(
    httpRequestMaker: HttpRequestMaker,
    system: ActorSystem) extends WinNoticeRequestMaker {

  import system.dispatcher

  override def sendWinNotice(nurl: String) = {
    httpRequestMaker.proccess(HttpRequestModel(nurl))
  }

  override def getAdMarkup(nurl: String) = {
    val fResponse = httpRequestMaker.proccess(HttpRequestModel(nurl))
    fResponse.map{response =>
      response.body._2
    }
  }
}
