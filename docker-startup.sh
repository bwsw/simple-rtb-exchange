#!/bin/sh
if [ -z ${1} ]
    then java -jar rtb-exchange.jar
    else java -Dconfig.resource=application.${1}.conf -jar rtb-exchange.jar
fi
