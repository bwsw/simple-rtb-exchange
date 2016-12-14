FROM openjdk:8-alpine

# Define working directory.
WORKDIR /data

ARG APP_PATH

COPY $APP_PATH rtb-exchange.jar

ENV env=

# Run rtb-exchange.
CMD if [[ -z ${env} ]]; \
        then java -jar rtb-exchange.jar; \
        else java -Dconfig.resource=application.${env}.conf -jar rtb-exchange.jar; \
    fi

EXPOSE 8081
