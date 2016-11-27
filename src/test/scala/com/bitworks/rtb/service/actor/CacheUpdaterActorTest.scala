package com.bitworks.rtb.service.actor

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import com.bitworks.rtb.service.dao.CacheUpdater
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.service.actor.CacheUpdaterActor CacheUpdaterActor]].
  *
  * @author Egor Ilchenko
  */
class CacheUpdaterActorTest extends FlatSpec with Matchers with EasyMockSugar {

  implicit val system = ActorSystem("test")

  "CacheUpdaterActor" should "call cache updater on init cache message received" in {
    val message = InitCache

    val cacheUpdater = mock[CacheUpdater]
    expecting {
      cacheUpdater.notifyAll(message) times 1
    }

    whenExecuting(cacheUpdater) {
      val actorRef = TestActorRef(new CacheUpdaterActor(cacheUpdater))
      actorRef ! message
    }
  }

  it should "call cache updater on update cache message received" in {
    val message = UpdateCache

    val cacheUpdater = mock[CacheUpdater]
    expecting {
      cacheUpdater.notifyAll(message) times 1
    }

    whenExecuting(cacheUpdater) {
      val actorRef = TestActorRef(new CacheUpdaterActor(cacheUpdater))
      actorRef ! message
    }
  }

}
