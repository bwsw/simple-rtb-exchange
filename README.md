RTB exchange
==============

This project provides RTB exchange implemented according to [OpenRTB API Specification 2.3](https://github.com/openrtb/OpenRTB/blob/master/OpenRTB-API-Specification-Version-2-3-FINAL.pdf).

Requirements
------------

* [Sbt](http://www.scala-sbt.org/)
* [Docker](https://www.docker.com/) 


Usage
------

### Build jar file with dependencies

Building requires setting up database for tests or adding `test in assembly := {}` in
[build.sbt](build.sbt) for disabling tests.

Command `sbt assembly` builds *target/scala-2.11/rtb-exchange-\<VERSION\>-jar-with-dependencies.jar*.


### Build docker image

    
Building requires setting up database for tests or adding `test in assembly := {}` in
[build.sbt](build.sbt) for disabling tests.

    make build [IMAGE_NAME=<image_name>]
    
* *IMAGE_NAME=\<image_name\>* &mdash; name of docker image. Default *rtb-exchange*.


### Set up database for tests

To start database for tests use command
    
    make db_up
    
To shutdown this database use command

    make db_down


### [Set up working database](db/README.md)


### Run application via jar file

Before running application you should set up working database.

    java -Dconfig.resource=application.<env>.conf -jar <PATH-TO-JAR>
    
* *application.\<env\>.conf* &mdash; file with configuration for environment *\<env\>*. Default value
is [*application.prod.conf*](src/main/resources/application.prod.conf) is already contained in jar file.


### Run application in docker

    docker run -d -p<port>:8081 [-v <log_path>:/data/logs] [--env env=<env>] <image_name>
    
* *\<env\>* &mdash; application environment (default *prod*). Current directory should contain 
*application.\<env\>.conf* file if this parameter has been set.
* *\<port\>* &mdash; listen port. Default value is *8081*.
* *\<log_path\>* &mdash; directory for log files. Default value is *./logs*.
* *\<image_name\>* &mdash; name of docker image.
 

### [Run E2E tests](e2e/README.md)
