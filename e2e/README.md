# E2E Testing
## Configuration
test_cases contains pairs: **\<sql\>,\<collection\>**
directory for sql files is _sql_
directory for collections is _collections_
## Usage
### Run tests using sbt task
Preferable way of running e2e tests is to run **sbt testE2E**
This command has optional arguments:
**sbt "testE2E \<env\> \<bidders-host\> \<report-path\>"**
Where:
- \<env\> environment for DB and tests (default = e2e),
- \<bidder_host\> base url for fake bidders (default = rtb-ci.z1.netpoint-dc.com:8083),
- \<report_path\> path for newman reports (default ../target/test-reports/)
### Run tests using Makefile
In e2e directory:
**make run ENV=\<env\> BIDDER_HOST=\<bidder_host\> REPORT_PATH=\<report_path\> ASSEMBLY=\<assembly\>**
Where 
- \<env\> environment for DB and tests (default = e2e),
- \<bidder_host\> base url for fake bidders (default = rtb-ci.z1.netpoint-dc.com:8083),
- \<report_path\> path for newman reports (default ../target/test-reports/),
- \<assembly\> path for application executable

e.g. _make run ASSEMBLY=../target/scala-2.11/rtb-assembly.jar_

