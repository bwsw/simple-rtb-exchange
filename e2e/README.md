E2E Testing
============

Configuration
-------------

The [*test_cases*](test_cases) contains pairs *\<sql\>,\<collection\>*.

* *\<sql\>* &mdash; name of file with sql query that updates table *bidder* in database.
    Located in [*sql/*](sql/).
* *\<collection\>* &mdash; name of file with collection for [newman](https://github.com/postmanlabs/newman)
    that contains tests. Located in [*collections/*](collections/).

Usage
------

### Run tests using Makefile

To run E2E tests use this command

    make execute BIDDER_HOST=<bidder_host> [REPORT_PATH=<report_path>] [LOGPATH=<log_path>] [IMAGE_NAME=<image_name>]

* *BIDDER_HOST=\<bidder_host\>* &mdash; base url for fake bidders.
* *REPORT_PATH=\<report_path\>* &mdash; path for test reports. Default value is *../target/test-reports/*.
* *LOGPATH=\<log_path\>* &mdash; path for applications logs. Default value is *../target/logs/*.
* *IMAGE_NAME=\<image_name\>* &mdash; name of docker image. Default value is *rtb-exchange*.
