OpenRTB compatible exchange server core
==============

This project provides RTB exchange implemented for training purposes.

Introduction
------------

RTB exchange is based on
[OpenRTB API Specification 2.3](https://github.com/openrtb/OpenRTB/blob/master/OpenRTB-API-Specification-Version-2-3-FINAL.pdf)
for bidding but has its own API. 

A basic RTB exchange functionality was implemented. 

Supported formats: JSON.

A list of features that are not supported in the current version:

* Multiple currencies support. RTB Exchange consider any currency as USD.
* Private Marketplace. Even though deal ids are being sent in the request, they will not be considered for any bidding strategy.
* Segments.
* Data providers.
* Macro encoding.
* Avro and Protobuf formats.
* Statistics (e.g. requests, impressions).


Requirements
------------

* [sbt](http://www.scala-sbt.org/)
* [Docker](https://www.docker.com/) 

Usage
------

### Build jar file with dependencies

Building requires setting up database for tests or setting `test in assembly := {}` for disabling tests.

The command `sbt assembly` builds *target/scala-2.11/rtb-exchange-\<VERSION\>-jar-with-dependencies.jar*.

### Build docker image

This project provides two options for docker images:

using Nexus
    
    docker build -t <image name> -f Nexus.dockerfile --build-arg URL=<nexus URL> --build-arg USERNAME=<nexus user> --build-arg PASSWORD=<nexus user password> .
    
using a ready jar
    
    docker build -t <image name> --build-arg APP_PATH=<path to jar with dependencies> .
    
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

    docker run -d -p<port>:8081 [-v <log_path>:/opt/rtb-exchange/logs] [--env env=<env>] <image_name>
    
* *\<env\>* &mdash; application environment (default *prod*). Current directory should contain 
*application.\<env\>.conf* file if this parameter has been set.
* *\<port\>* &mdash; listen port. Default value is *8081*.
* *\<log_path\>* &mdash; directory for log files. Default value is *./logs*.
* *\<image_name\>* &mdash; name of docker image.
 
### [Run E2E tests](e2e/README.md)

