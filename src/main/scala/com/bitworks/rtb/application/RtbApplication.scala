package com.bitworks.rtb.application

import akka.actor.ActorSystem
import com.bitworks.rtb.service.actor.CacheUpdaterActor
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import com.bitworks.rtb.service.Configuration
import scaldi.akka.AkkaInjectable._

/**
  * Entry point.
  *
  * @author Egor Ilchenko
  */
object RtbApplication {
  implicit val module = new RtbModule
  implicit val system = inject[ActorSystem]

  val config = inject[Configuration]

  def main(args: Array[String]): Unit = {
    runCacheUpdater()
  }

  /** Inits cache and starts cache update scheduler  */
  private def runCacheUpdater() = {
    val receiver = injectActorRef[CacheUpdaterActor]
    receiver ! InitCache

    import system.dispatcher

    system.scheduler.schedule(
      config.cacheUpdateInterval,
      config.cacheUpdateInterval,
      receiver,
      UpdateCache)
  }
}
