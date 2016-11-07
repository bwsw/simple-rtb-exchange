package com.bitworks.rtb.service.dao

import com.bitworks.rtb.service.dao.schema._
import io.getquill._

/**
  * Main DB context for query execution.
  *
  * @author Egor Ilchenko
  */
class DbContext(configPrefix: String)
  extends JdbcContext[PostgresDialect, SnakeCase with LowerCase](configPrefix) {

  /** DB schema defines. */
  object Schema {
    def publisher = quote {
      querySchema[PublisherEntity]("publisher")
    }

    def publisherCategory = quote {
      querySchema[PublisherCategoryEntity]("publisher_category")
    }

    def blockedCategory = quote {
      querySchema[PublisherBlockedCategoryEntity]("blocked_category")
    }

    def blockedAdvertiser = quote {
      querySchema[PublisherBlockedAdvertiserEntity]("blocked_advertiser")
    }

    def iabCategory = quote {
      querySchema[IABCategoryEntity]("iab_category")
    }

    def bidder = quote {
      querySchema[BidderEntity]("bidder")
    }

    def displayManager = quote {
      querySchema[DisplayManagerEntity]("display_manager")
    }

    def siteDisplayManager = quote {
      querySchema[SiteDisplayManagerEntity]("site_display_manager")
    }

    def site = quote {
      querySchema[SiteEntity]("site")
    }

    def siteCategory = quote {
      querySchema[SiteCategoryEntity]("site_category")
    }
  }

}
