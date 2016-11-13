package com.bitworks.rtb.service.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.bitworks.rtb.model.response.BidResponse
import scaldi.Injector

/**
  * Actor responsible for sending win notice and getting ad request.
  *
  * @author Egor Ilchenko
  */
class WinActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg: BidResponse =>
      log.debug("bid response received")
      sender ! msg
  }
}

object WinActor {

  /** Returns Props for [[com.bitworks.rtb.service.actor.WinActor WinActor]]. */
  def props(implicit inj: Injector) = {
    Props(new WinActor)
  }
}
