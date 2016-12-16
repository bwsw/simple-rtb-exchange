package com.bitworks.rtb.service.dao

import com.bitworks.rtb.model.message.{InitCache, UpdateCache}
import org.scalatest.FlatSpec
import org.scalatest.easymock.EasyMockSugar

/**
  * Test for [[com.bitworks.rtb.service.dao.CacheUpdater CacheUpdater]].
  *
  * @author Egor Ilchenko
  */
class CacheUpdaterTest extends FlatSpec with EasyMockSugar {

  "CacheUpdater" should "notify all registered DAOs correctly" in {
    val updater = new CacheUpdater

    val initMessage = InitCache
    val updateMessage = UpdateCache

    val first = mock[BaseDao[Any]]
    val second = mock[BaseDao[Any]]
    expecting {
      first.notify(initMessage) times 1
      second.notify(initMessage) times 1

      first.notify(updateMessage) times 1
      second.notify(updateMessage) times 1
    }
    whenExecuting(first, second) {
      updater.register(first)
      updater.register(second)

      updater.notifyAll(initMessage)
      updater.notifyAll(updateMessage)
    }
  }

}
